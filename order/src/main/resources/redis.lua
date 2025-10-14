if (redis.call('EXISTS', KEYS[1]) == 0) then
    return -2
end
local stock = tonumber(redis.call('GET', KEYS[1]));
local decrBy = tonumber(ARGV[1]);
if (decrBy == nil or decrBy <= 0) then decrBy = 1 end
if (stock == nil) then return -2 end
if (stock < decrBy) then return -1 end
return redis.call('DECRBY', KEYS[1], decrBy);
