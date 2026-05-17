local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
--local current_time = tonumber(ARGV[3])
--local requested = tonumber(ARGV[4])
local current_time = 0
local requested = 1

if not current_time then
	return redis.error_reply("Missing current_time")
end

--get stored values
local data = redis.call("HMGET", key, "tokens", "last_refill")
local tokens = tonumber(data[1])
local last_refill = tonumber(data[2])

if tokens == nil then
	tokens = capacity
	last_refill = current_time
end

--refill tokens
local delta = math.max(0, current_time - last_refill)
local refill = delta * refill_rate
tokens = math.min(capacity, tokens + refill)

local allowed = tokens >= requested

if allowed then
	token = tokens - requested
end

--save updated state
redis.call("HMSET", key,
"tokens", tokens,
"last_refill", current_time
)

--expiry key to avoid memory leak
redis.call("EXPIRE", key, 60)

return allowed