
-- 传入的key
local key=KEYS[1]

local time = tonumber(ARGV[1])
local count = tonumber(ARGV[2])

-- redis.call()  调用redis命令，，，  key：当前限流的接口
local current  = redis.call("get",key)



-- current 可能有值，，  可能没有值： 没有被调用过的接口，
if(current and tonumber(current) > count) then
    -- 超过最大次数
    return tonumber(current)
end

current = redis.call("incr",key);

-- 并发条件下，，这个incr会被执行多次
if tonumber(current)==1 then
    -- 只有这个线程在操作
    redis.call("expire",key,time)
end

-- 之前已经有线程操作过了，设置过了expire
return tonumber(current)

-- 返回current的值，在java端判断是否限流