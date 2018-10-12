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

 *  redis的偏移量都是从0开始
 
 *  migrate在redis开启密码验证下运行会报错,应该去掉密码验证后再使用
 
 *  dump序列化key时,client以--raw连接时不会返回结果
 
 
 
 redis相关配置:
    
 *  requirepass lzx  //为redis设置密码
 *  bind 127.0.0.1  //Redis的默认配置会接受来自任何地址发送来的请求.即在任何一个拥有公网IP的服务器上启动Redis服务器,都可以被外界直接访问到.
                      这里设置为只允许本机应用连接Redis
 *  ... 
 
 
 
 
 
 *  操作key的相关命令:
 
    keys pattern    //查找符合模式pattern的key
    exists key      //检查key是否存在
    type key        //返回key所储存的值的类型
    randomkey       //从数据库随机返回一个key(不删除)
    del key [key ...]  //删除给定的一个或多个key,不存在的key会被忽略
    move key db  //将当前数据库的key移动到给定的数据库db当中
    rename key newkey  //将key改名为newkey,当key和newkey相同,或者key不存在时返回一个错误,当newkey存在时则覆盖旧值
    renamenx key newkey  //类似于rename,不同在于当且仅当newkey不存在时才重命名
    
    scan key cusor [MATCH pattern] [COUNT count]  //迭代当前数据库中的数据库键key
    
    expire key seconds  //为给定key设置生存时间(以秒为单位),当key过期时(生存时间为0),它会被自动删除
    expireat key timestamp  //类似expire,不同在于接受的时间参数是UNIX时间戳
    pexpire key millseconds  //类似expire,不同在于以毫秒为单位
    pexpureat key milliseconds-timestamp  //类似expireat,不同在于以毫秒为单位
    ttl key  //以秒为单位返回key的剩余生存时间
    pttl key  //以毫秒为单位返回key的剩余生存时间
    persist key  //移除给定key的生存时间
    
    dump key //序列化指定key(以--raw连接时不会返回结果)
    restore key seconds serialized-value [replace]  //详见http://redisdoc.com/
    
    sort  //详见http://redisdoc.com/
    migrate  //此命令在redis开启密码验证下运行会报错,应该去掉密码验证后再使用.详见http://redisdoc.com/和https://blog.csdn.net/sunhuiliang85/article/details/74352446
    object  //详见http://redisdoc.com/
    
    
    
    
    
   
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
    incrby key increment  //将key中存储的数值加上increment(整数,increment可以是正数或负数)
    incrbyfloat key increment  //将key中存储的数值加上increment(浮点数,increment可以是正数或负数)
    decr key  //将key中存储的数值减1
    decrby key decrement  ///将key中存储的数值减去increment(整数,increment可以是正数或负数)
 
 
 
 
 
 
 *  操作hash(哈希表)的相关命令(hash是一个string类型的field和value的映射表):
 
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
    
    hincrby key field increment  //为哈希表key中field对应的值加上增量increment(整数,increment可以是正数或负数)
    hincrbyfloat key field increment  //为哈希表key中field对应的值加上增量increment(浮点数,increment可以是正数或负数)
 
 
 
 
 
 
 *  操作list(列表)的相关命令(list是简单的字符串列表):
    
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
    
    
    
    
      
  
 *  操作set(集合)的相关命令(set是String类型的无序集合):  
 
    sadd key member [member ...]  //将一个或多个member元素加入到集合key当中,已经存在于集合的member元素将被忽略
     
    smembers keu  //返回集合key中的所有成员
    scard key  //返回集合key的基数(集合中元素的数量)
    sismember key member  //判断member元素是否集合key的成员
    srandmember key [count]  //随机返回集合的元素
        不指定count: 随机返回集合中的一个元素
        count>0: 如果count小于集合基数,随机返回一个包含count个元素的数组,数组中的元素各不相同
                 如果count大于等于集合基数,返回整个集合   
        count<0: 随机返回一个数组,数组中的元素可能会重复出现多次,并且数组长度为count的绝对值
    sscan key cusor [MATCH pattern] [COUNT count]  //迭代集合键中的元素
        
    srem key member [member ...]  //移除集合key中的一个或多个member元素,不存在的members元素会被忽略
    spop key [count]  //从key中移除并返回count个随机元素,不设置count时移除并返回一个随机元素  
    smove source destination member //将member元素从source集合移除并添加到destination集合
    
    sdiff key1 [key2 ...]  //返回一个集合的全部成员,该集合是所有给定集合之间的差集(key1-key2-...-keyn)
    sdiffstore destination key1 [key2 ...]  //将所有给定集合之间的差集(key1-key2-...-keyn)保存到destination集合,如果destination已经存在则覆盖
    sinter key [key ...]  //返回一个集合的全部成员,该集合是所有给定集合的交集
    sinterstore destination key [key ...]  //将所有给定集合的交集保存到destination集合,如果destination已经存在则覆盖
    sunion key [key ...]  //返回一个集合的全部成员,该集合是所有给定集合的并集
    sunionstore destination key [key ...]  //将所有给定集合的并集保存到destination集合,如果destination已经存在则覆盖
 
 
 
 
 
 
 *  操作sorted set(有序集合)的相关命令(set是String类型的有序集合,每个元素都会关联一个double类型的分数,通过分数进行排序): 
    
    zadd key score member [[score member] ...]  //将一个或多个member元素及其score值加入到有序集key当中
    
    zincrby key increment member  //为有序集key的成员member的score值加上增量increment(整数,increment可以是正数或负数)
        key不存在或member不存时相当于zadd key increment member
        
    zrange key start stop [withscores]  //返回key中指定区间start-stop(包括start和stop)内的元素(按score值从小到大排序)
    zrevrange key start stop [withscores]  //类似zrange,不同在于成员按score值从大到小排列
    zrangebyscore key min max [withscores] [limit offset count]  //返回key中所有score值介于min和max之间(可以包括min和max也可以不包括)的成员(按score值从小到大排序)
    zrevrangebyscore key max min [withscores] [limit offset count]  //类似zrangebyscore,不同在于min和max互换位置(实际上还是表示min-max区间),成员按score值从大到小排列
    zscan key cusor [MATCH pattern] [COUNT count]  //迭代有序集合中的元素(包括member和score)
    zcard key  //返回有序集key的基数
    zscore key member  //返回有序集key中,成员member的score值
    zcount key min max  //返回有序集key中,score值在min和max之间(包括min和max)的成员的数量
    zrank key member  //返回有序集key中成员member的排名(从0开始,其中有序集成员按score值从小到大排列)
    zrevrank key member  //类似zrank,不同在于有序集成员按score值从大到小排列
    
    zrem key member [member ...]  //移除有序集key中的一个或多个成员,不存在的成员将被忽略
    zremrangebyrank key start stop  //移除有序集key中,排名(rank)在区间(start-stop之间包括start和stop)内的所有成员
    zremrangebyscore key min max  //移除有序集key中,score值介于min和max之间(可以包括min和max也可以不包括)的成员
    
    zunionstore destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]  //计算给定的一个或多个有序集的并集存储到destination,destination存在则覆盖
        numkeys: 给定 key 的数量
        WEIGHTS: 每个给定有序集的所有成员的score值在传递给聚合函数(aggregation function)之前分别乘对应的weight
        ARRREGATE: 
            SUM: 将所有集合中某个成员的score值之和作为结果集中该成员的score值,默认为SUM
            MAX: 将所有集合中某个成员的最大score值作为结果集中该成员的score值
            MIN: 将所有集合中某个成员的最小score值作为结果集中该成员的score值
    zinterstore destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX] //类似zunionstore,不同在于计算的是交集
 
    zrangebylex key min max [limit offset count]  //当有序集合的所有成员都具有相同的分值时,元素会根据成员的字典序来进行排序,返回key中member值介于min和max之间的member(可以包括min和max也可以不包括),如果有序集合里面的成员带有不同的分值,那么命令返回的结果是未指定的
    zlexcount key min max  //类似zrangebylex,不同在于zlexcount是统计元素数量
    zremrangebylex key min max  //类似zrangebylex,不同在于zremrangebylex是移除元素
 
 
 
 
 
 
 
 
  