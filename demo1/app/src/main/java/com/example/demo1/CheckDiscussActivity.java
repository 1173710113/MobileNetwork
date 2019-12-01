package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CheckDiscussActivity extends AppCompatActivity {
    private String[] data={"我在澳洲上学，经常坐飞机十一二个小时，就算是中途转机的，也至少有一程是八小时以上。国际航班的头等舱很贵，出国留学的好多同学家里很有钱，但大部分人还是选择经济舱。但是呢，长途飞行，经济舱真的很累，尤其是要过夜的时候，很不舒服。所以有一次在一个假期前夕，我们一群中国学生在一起聊天的时候，有人就抱怨说：\n" +
            "\n" +
            "作者：小莫\n" +
            "链接：https://www.zhihu.com/question/49432258/answer/846823681\n" +
            "来源：知乎\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "以前公司的老外到中国来出差，我都觉得他们是神人，下了飞机就直接去见客户了，十多个小时的国际飞行，也不用倒时差，也不用休息。后来有一段时间公司状况不好，一律全是经济舱了，我还按照惯例给老外安排了下飞机就直接见客户的行程，老外就怂了，根本不去，要求去酒店，我才知道，原来头等舱可以吃的好，可以躺下来睡的好，西服有人给挂起来，不像坐经济舱那样会皱成一团，根本不是因为老外体力好，是因为丫原来都是坐头等舱来的。\n" +
            "\n" +
            "作者：eric\n" +
            "链接：https://www.zhihu.com/question/49432258/answer/598942871\n" +
            "来源：知乎\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "其实头等舱和国际连锁五星级酒店一样，很多消费者花的并不是自己的钱。最典型的就是企业中高层管理者。无论是效益好的大企业还是效益没那么好的小公司，只要公司成长到一定规模，势必会给不同层级的员工制定不同层次的差旅费用标准。刚入行的小销售当然只能坐经济舱，住如家，而上了一定级别的管理者，自然就可以享受商务舱/头等舱+专车接送+五星级酒店这样的待遇了。\n" +
            "\n" +
            "作者：清蒸高战蟹\n" +
            "链接：https://www.zhihu.com/question/49432258/answer/240331431\n" +
            "来源：知乎\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "某日某航司，某国际航班上。淡季，八个头等坐了一个客人，青年才俊，仪表堂堂，约等于颜值再高十倍的王校长。于是六个空姐与客人围坐聊天，好吧，没有“围坐”，领会精神……聊到为何出行，头等客人叹气道，家里人相约结伴出游，国外某地趴体，结果大家都坐家中的私人飞机走了，只有他因为公务耽搁，没有赶上飞机。迫不得已，“只好”乘坐公共交通，毕竟让家里的飞机回来单独接自己不太划算。所以我们万万没想到，别人乘坐头等舱，居然是为了“勤俭节约”而已……\n" +
            "\n" +
            "作者：hypnos\n" +
            "链接：https://www.zhihu.com/question/49432258/answer/218342636\n" +
            "来源：知乎\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "这个插话的女孩家庭条件很不错，平时上课也背大牌包包。她确实是没怎么坐过经济舱，也没有想要炫耀或者装x的意思，她就是单纯的觉得头等舱舒服啊。因为first class的价格对她来说不值一提，并且能提供给她舒适的环境，在能承担的情况下为什么不选择高端的品质呢。\n" +
            "\n" +
            "作者：小莫\n" +
            "链接：https://www.zhihu.com/question/49432258/answer/846823681\n" +
            "来源：知乎\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。",
            "过完安检查看登机口，走去相对应离登机口最近的休息室。找好位置，放好行李，就去冰箱拿饮料，打开手提，查收邮件，做好今天的工作报告，把工作上的事情做好，还有余的时间看下报刊，坐在按摩椅按摩身体，让舟车劳顿的身体放松，等待某航的服务人员叫自己的那班飞机开始登机"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_discuss);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckDiscussActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ScrollListView) findViewById(R.id.list_comment);
        listView.setAdapter(adapter);
    }
}
