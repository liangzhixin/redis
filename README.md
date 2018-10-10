# redis（更多命令见http://redisdoc.com/）

 *  redis优势:
 *  1.性能极高 - Redis能读的速度是110000次/s,写的速度是81000次/s
 *  2.丰富的数据类型 - 支持string,list,set,zset,hash等数据结构的存储
 *  3.原子性 - 单个操作是原子性的,多个操作也支持事务(即原子性)
 *  4.丰富的特性 - Redis还支持publish/subscribe,通知,key过期等等特性
 *  5.支持数据的持久化以及数据的备份
 
 
 
 注意以下问题:
 
 *  启动方式1：redis-server D:\Redis\redis.windows.conf  //启动的时候指定配置文件
 *  启动方式2：redis安装为服务(安装服务的时候指定配置文件)并启动redis服务: redis-server --service-install D:\Redis\redis.windows.conf 
 *  config set requirepass new_pwd  //设置密码（redis服务不重启就一直有效）

 *  redis连接:
 * 	redis-cli -h host -p port -a password --raw	//连接到redis服务（--raw表示支持中文）

 *	序列化key的问题:
 *	dump key_name  //序列化key（以--raw连接时不会返回结果）

 *  redis的偏移量都是从0开始
 
 
 
 *  操作key的相关命令:
 
    keys pattern    //查找符合模式pattern的key
    exists key      //检查key是否存在
    randomkey       //从数据库随机返回一个key(不删除)
    ...
    
    
    
    
    
   
 *  操作string(字符串)的相关命令:
 
    set key value [ex seconds] [px milliseconds] [nx|xx]  //设置key的值为value
    setex key seconds value  //设置key的值为value,过期时间为seconds(s)
    psetex key milliseconds value  //设置key的值为value,过期时间为milliseconds(ms)
    setnx key value  //设置key的值为value当且仅当key不存在时
    mset key value [key value ...]  //设置一个或多个key-value对
    msetnx key value [key value ...]  //设置一个或多个key-value对当且仅当key都不存在(要么全部成功,要么全部失败)
    setrange key offset value  //将key偏移量从offset开始之后的字符串用value覆盖
    append key value  //key存在时将value添加到原来值的末尾,key不存在则相当于set key value
    
    get key  //返回key的value值
    mget key [key ...]  //返回一个或多个key的value值
    getrange key start end  //返回key对应的value的偏移量从start-end(包括start和end)的子字符串 
    getset key value  //相当于get key然后set key value  
    strlen key  //返回key所储存的字符串值的长度
    
    setbit key offset value  //设置key指定偏移量上的位为value(0或1)
    getbit key offset  //返回key指定偏移量上的位
    bitcount key [start] [end]  //计算key对应的字符串(或者是偏移量为start-end的子字符串)中，被设置为1的比特位的数量
    bittop operation destkey key [key ...]  //对一个或多个key对应的保存二进制位的字符串进行位操作，并将结果保存到 destkey 上
    bitfield ...  //详见http://redisdoc.com/
    
    incr key  //将key中存储的数值加1
    incrby key increment  //将key中存储的数值加上increment(整数)
    incrbyfloat key increment  //将key中存储的数值加上increment(浮点数)
    decr key  //将key中存储的数值减1
    decrby key decrement  ///将key中存储的数值减去increment(整数)
 
 
 
 
 
 
 *  操作hash(哈希表)的相关命令:
 
    hset key field value  //将哈希表key中field的值设为value
    hsetnx key field value  //将哈希表key中field的值设为value当且仅当field不存在
    hmset key field value [field value ...]  //同时将一个或多个field-value对设置到哈希表key中
    
    hkeys key  //返回哈希表key中的所有field
    hvals key  //返回哈希表key中所有field对应的值
    hget key field  //返回哈希表key中field对应的值
    hmget key field [field ...]  //返回哈希表key中一个或多个field对应的值
    hgetall key  //返回哈希表key中所有的field和value
    hscan key cusor [MATCH pattern] [COUNT count]  //迭代哈希表中的键值对
    hexists key field  //查看哈希表key中field是否存在
    hlen key  //返回哈希表key中键值对的数量
    hstrlen key field  //返回哈希表key中field对应的值的字符串长度
    
    hdel key field [field ...]  //删除哈希表key中的一个或多个field,不存在的field将被忽略
    
    hincrby key field increment  //为哈希表key中field对应的值加上增量increment(整数)
    hincrbyfloat key field increment  //为哈希表key中field对应的值加上增量increment(浮点数)
 
 
 
 
 
 
 *  操作list(列表)的相关命令:
    
    lpush key value [value ...]  //将一个或多个值value插入到列表key的表头(最左边)
    lpushx key value  //将值value插入到列表key的表头,当且仅当key存在并且是一个列表
    rpush key value [value ...]  //将一个或多个值value插入到列表key的表尾(最右边)
    rpushx key value  ////将值value插入到列表key的表尾,当且仅当key存在并且是一个列表
    linsert key before|after pivot value  //将值value插入到列表key当中,位于值pivot之前或之后
 
    lpop key  //移除并返回列表key的头元素
    rpop key  //移除并返回列表key的尾元素
    rpoplpush source destination  //将列表source中的最后一个元素弹出并且插入到列表destination的头部
    blpop key [key ...] timeout  //移除第一个非空列表的头元素(当给定列表内没有任何元素可供弹出,连接将被阻塞,直到等待超时或发现可移除元素为止)
    brpop key [key ...] timeout  //类似blpop
    brpoplpush source destination timeout  //类似blpop
    lrem key count value  //根据参数count的值,移除列表中与value相等的元素
        count>0: 从表头开始向表尾搜索,移除与 value 相等的元素.数量为 count
        count<0: 从表尾开始向表头搜索,移除与 value 相等的元素,数量为 count 的绝对值
        count=0: 移除表中所有与 value 相等的值
    
    lset key index value  //将列表key下标为index的元素的值设置为value
    
    ltrim key start stop  //让列表只保留指定区间start-stop(包括start和stop)内的元素,不在指定区间之内的元素都将被删除
        start>最大下标则相当于清空列表,eg: key下标为0-6,start=7,stop=9,则清空列表的所有元素
        stop>最大下标则将stop设置为最大下标,eg: key下标为0-6,start=2,stop=9,则保留下标为2-6的元素
    
    lindex key index  //返回列表key中,下标为index的元素
    lrange key start stop  //返回列表key中指定区间start-stop(包括start和stop)内的元素
    llen key  //返回列表key的长度