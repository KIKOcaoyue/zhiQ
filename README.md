# zhiQ
you are share your ideas on zhiQ.
No matter who you are, you are ideaman.

人人都是思想家。

zhiQ是一个分享点子，提出问题，解决问题的社交平台。
这里没有阶级制，没有会员制，人人平等。只关注问题，只解决问题。在这里，只要你不想，没有人可以知道你的信息，没有人可以知道你的学历，你的性别，
你的年龄，你是家庭，你的一切...

只要你的观点令人赞同，你的回答令人信服，不论你是谁，你都会受到人们的尊敬。


------------------------------------------------------------------------------------------------------------------

以下是技术文档。

本项目采用android(前端)+bmob(后端)进行构建。

项目定义为：即时通讯+社交平台

2020.06.14

bmob查询数据库使用的接口是一个匿名内部类，起先怎么都无法把获取到的对象给传出来，最终发现bmob查询数据库应该是一个异步的动作，于是直接使用handler接受
bmob查询发送的消息。bmob查询后会发送一个Message，供handler接收。


