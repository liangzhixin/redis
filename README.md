# redis（更多命令见http://redisdoc.com/）

 *  redis优势:
 *  1.性能极高 - Redis能读的速度是110000次/s,写的速度是81000次/s
 *  2.丰富的数据类型 - 支持string,list,set,zset,hash等数据结构的存储
 *  3.原子性 - 单个操作是原子性的,多个操作也支持事务(即原子性)
 *  4.丰富的特性 - Redis还支持publish/subscribe,通知,key过期等等特性
 *  5.支持数据的持久化以及数据的备份
 
 
 
 注意以下问题:

 *  redis连接:
 *	redis-server D:\Redis\redis.windows.conf  //启动的时候指定配置文件
 * 	redis-cli -h host -p port -a password --raw	//连接到redis服务（--raw表示支持中文）

 *	序列化key的问题:
 *	dump key_name  //序列化key（以--raw连接时不会返回结果）

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 


