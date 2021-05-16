package top.goodz.future.domain.task;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Description 任务
 * @Author Yajun.Zhang
 * @Date 2020/7/7 22:38
 */
@Service
public class AutoCreateDataServiceTask implements Callable<AutoTaskDataEntity> {

    private static final Logger logger = LoggerFactory.getLogger(AutoCreateDataServiceTask.class);


    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    private AutoTaskDataEntity createDateInfo() {

        AutoTaskDataEntity autoTaskDataEntity = new AutoTaskDataEntity();
        autoTaskDataEntity.setUserName(getChineseName());
        autoTaskDataEntity.setEmail(getEmail());
        autoTaskDataEntity.setPhone(getTel());
        autoTaskDataEntity.setIdCard(generateIdCard());
        Map<String, Object> map = identityCard(generateIdCard());
        autoTaskDataEntity.setSex((String) map.get("sex"));
        autoTaskDataEntity.setAge((int) map.get("age"));
        ProvinceInfo proCity = getProCity();
        autoTaskDataEntity.setProvince(proCity.province);
        autoTaskDataEntity.setCity(proCity.city);
        autoTaskDataEntity.setAreaRoad(proCity.areaRold);

        return autoTaskDataEntity;
    }


    /**
     * 生成中文名称
     *
     * @return
     */
    private String getChineseName() {

        //百家姓
        final String surname = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟";


        //男生名
        final String boyName = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽" +
                "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘公孔亢勾中之丹井介今内及太天屯斗斤牛丑支允元勿午友尤尹引文月日牙王云匀牛四丑仁什切升收壬少心手日氏水尤仍双尺仇止才不分匹化卡卞肥反夫巴幻户方木比毛仆丰火片" +
                "古切可瓜甘刊五丘加去句叫外巧巨玉甲令加占主巨冬他代只仗另句召尼正田旦奴凸立叮仝伏台奶凹五外央右未永以戊玉瓦由幼仕巧丘仙兄司且史左世出市玄仔冉穴示生申充主仞仟册加去只叫求正甲申石匝甩丙平母弘末包本弗北必丕半布皿目乏禾皮疋矛乎付兄卉戊民冰玄白卯" +
                "伉光匡共各考交件陇酿鹰霭坝嬖价企伍伎仰吉圭曲机艮六仲吉州朱兆决匠地旨朵吏下上乞士夕千子寸列年劣同打汀至臼灯竹老舟伎吊吏圳的宅机老肉虫伊仰伍印因宇安屹有羊而耳衣亦吃夷奸聿丞企休任先全再冲刑向在夙如宅守字存寺式戌收早旭旬曲次此求系肉臣自舌血行圳西休交件企匠吉尖而至色伏后名回好妃帆灰牟百份米伐亥卉冰刑合向旭朴系行汜复" +
                "克告改攻更杆谷况伽估君吴吸吾圻均坎研完局岐我扣杞江究见角言住占低佃况里冷伶利助努君吝昌壮妓妞局弄廷弟彤志托杖杜呆李江男究良见角具皂里舟佟你体足甸町豆吞玎位佐佘冶吾吟吴吻完尾巫役忘我修言邑酉吟吴研呆角七伸佐作些伽些初吹呈坐孝宋岐希岑床序巡形忍成杏材杉束村杞步汝汐池私秀赤足身车辰系占伺住余助劭劬邵吸坐壮妆局床志汕江灶见即却克早何布伯伴佛兵判别含坊坂吵宏旱每甫牡况免孚孝尾巫希庇形忙杏呆步汛贝儿" +
                "供侃刻卦固坤姑官冈庚快抗昆果空亟其具券卷奇委季宜居届岢岸杰佳京侄佳来例制到兔两典卷周呢坦奈妮宙定居屉帖底店征忝灒銮骥湾钻骧忠念技投政枝东林汰决玖知的直纠金两乳侏佰侗佻佬具冽卓拈妲妯宕岱岭帖帙底抒林杼沓炉竺长依侑味夜委宜宛岳岸岩往亚武於易昂旺沅沃汪物艾卧佯儿咏抑昀炎杳事享侍使侈然刹刺协卒洽沁取受步垂奇始炊姓妻妾尚屈弦所承昌升昔松欣沙沈社舍炊采长青幸亟徇佳舍儿争其刷券制效卷姐姒姗季炙宗届岫征承昔析枕状八并佩函和命坡坪奉孟帛水府佛彼忽或戽房扮枇扶放昏朋服明杭杯枚板沛沐汾版牧虎门阜杷盲非" +
                "冠奎皈客故柑柯况看科肝革屋癸砍禹轨页九亭亮柱俊侣冒段劲南姬姜姣宦帝度痔建峙待律怠急招拒拓拙拉昭架柱柳注治炭界皆突纪纣耐肚致计订军酊俐胃百厘咨姝姿柁沱炭妆纣重珏盅眈俄俞勇威娃姻姚姨屋幽彦奕哀哇玟怡押映昱韦油泳沿姚畏烟盈禹约耶衍要页音昱柚胤易信侠系俗促俏前则奏型姹妊姝姿室宣巷咱哉思性施昨是春星查柴栅柔染泉帅甚相省砂祈秋穿肖重首酉食香侵俟峙旭注沐炷祉贞昌泓侯保便冒勉匍奔品佩杯封哈皇拔抱怕柏柄泌法泡炳玫盆眉红美虹秒表负面风飞眄胃勃厚咸叛孩奂屏枰某河泛赴" +
                "库恭拱格桂根耕耿股肯贡高个刚哥宫径挂皋径徒倜恬拯指拿料旅晋朕桌桔桃桐洞流洛酒烈特玲珍真矩祝秩租站级纸纳纽者肩芝记讨酌酒针钉只挑借倒值俱倪倘伦兼唐哲娘旃娟娜展峻准凌洲套爹特留俩倜庭恫耻烙料栗株津玳畜砧恩按案鸟洋秧翁纹耘育芽芸蚊袁烟倚原员埃宴峨倚娱容峪晏移益差师席座徐恰息恕肩持拳拾时书曹校朔桑栽殊气洽珊祠神祖秦秤索素纱纾纯虔讫训财起轩芩闪迅倩幸修仓城夏孙宰容射峡厝叟奚畜春乘借准淞宵指拭牲洵洳狩兹珊炸租站宸挈旁晃桓活洪畔亩眠破炮秘粉纺肺肥航般芳芙花配马侯倍俯俸们圃埋娩峰肪涵畔埔害恢恍恒柏派洹玻泌珉祜呗" +
                "国寇昆康苦袍规贯够勘崞岗梗珙偕假二乃了力丁刀刁二又人入健停侦剪动翎念基坚堂堆婧寄湾灒銮骥湾钻骧专张得教救朗条梁梯械梨浙浪珠略皎眷窕竟第终累舵苓架诀近钓顶鸟将那庶振挺捉捐甜祭趾囵堆凌崃带帐徕悌画梁梃桶町娄伟偶务唯问婉寅尉帷庸悟悠悦敖晚梧浴眼研移胃苑英迎野鱼欲浣翌圉乾做区卿参售启商唱娶妇宿崇崎崔常强从悄叙旋晨晟族消爽犀祥绅细紫组绍婧羞习邢舷船茄若处术袖设讼责赦雀雪顷彩常孰侦匙圊执将专就峥崧巢庶彩悉施曹浙笙钏阡凰毫培婚婆妇密彬彪患斌曼海浩烽班瓶毕盒符邦胡背胞胖舶范茅苗袍被觅访货返贩闭麦麻邦壶票冕副埠屏涵捕敏皓梅第珩艴苹" +
                "敢款淦筐给贵辜开凯昆诒询几蛟植堵堤奠岚帧掌掘捷掏掎推探接敦景智晶替朝椒棠栈殖淘添淡净焦街诊理荔眨贴屠贷轸迢迪迦量钧钮间集杰劳单婷喋传塘塔暖楠殿渡汤帏幄惟掩椅涯液渊焰为异砚围茵越阮轶雁雅寓云雯媛喻贻婺焱琬琰畲劳博堡报富寒嵋帽幅帮弼复彭徨偏整理惠扉排斑酣普棉棒棚涵混淼淮淝牌画番发皓脉茗评贺费买贸迫邯闵防阪黄傅傍媒媚黑瓿" +
                "匮块干感揆手楷港琨莞夸鼓该贾传仅涂塔塘廊谦提敬斟极楠殿汤渡绢经茎莒获莅庄莉蜀里装解詹鼎贾路迹退铃钜陀电雷靖顿暖桢路嫁农贮贷贴轸迪钠湍琳当略铃鼓励庸园圆奥爱意扬援握榆业杨椰涌渝渭游炜爷烟兽犹煜碗筠义肄莞莠虞蛾裕诣郁钰雍阿预饮衙莹蓊晕渥琬琰畹筵裔淡催传勤势嗣塞嵩厦新喧楸楚岁湘测凑煦琴琪琦睡祺稔稠筮粲绣群圣莎裙诩诗试诠详资载送铅阻雌颂驰熙暄琼塞嵩想桢椿岁渚煮琛庄裟输轼幕汇惶挥描换楣枫湖浑渺涣煤烦琶琥盟睦碑禀聘腑荷莫号蜂补话酩附颁饭晕募焕" +
                "窥糕膏盖钢龟购器垦横橄丈女己巾勺丸也于篙馆尽坛导惮战撰整历瞳昙暨机橘洁潭灯瑾璋庐积筑蒸诸谛诺练猪赖蹄辑道达都录锦锭陆陶陵霓霖静颊头雕疆腿臻赚骆莅俦橙润澈笃缙萤卫谓谒谙谕豫逾游运阴余赢蓉蓊勋儒器学宪熹憧晓桥樵橙橡树润潮甑莳璇聪融亲谌谐输遂醒钱错陈陲赛蓄苍侪澍莼谊铮锡阊桦壁奋播抚横潘磨蒙衡谋讳讽辨锚陪默蒲蓓滨嬖凭颖蓉运阴馀" +
                "柜圹归缋蒉磬纩戴拟抬挤爵涛礼简粮职旧蕉谨转遮题镇储藜芷迟鲤翼芸讴医隘额瑷莸镒隗彝潍储丛曙湿织萧蕊蝉适双绣锁垒戴断柠涛泞璐礼粮藜槟滨获壁环蕃丰庞璧馥" +
                "扩关铿庐疆祷荐勒襟谭证轿辽邻郑邓类鲸丽麓镜觉际橹栎泺辚链韵稳薏薇膺臆蚁袄遗雾愿艳蓣蕹韫玺薛薪绳蟹识赞遵迁选庞渖荐谯镟锵镞铲际颡鹑暹攀簿绘鹏庞瀑宝祢薜谱镆" +
                "矿阚岿藉蓝拢沥泷竞篮罗舰觉警钟露腾党枥槠滤濑炉窦笞筹镦荩严瀛耀译议邀懿赢萨藏薰壤悬曦琼筹脐馨释献坏孀籍藉谵锈钟宝怀瀚缤还迈飘膑朦濒铧罐赣雳灵观戆颢逻驴湾" +
                "鹦鹳郁吁一画一乙七十几口干工弓久己土大弋厅篱钥瑷镶黉巳兀三小山川巳才凡";


        int index = getNum(0, surname.length() - 1);
        String first = surname.substring(index, index + 1);
        String str = boyName;
        int length = boyName.length();

        index = getNum(0, length - 1);
        String second = str.substring(index, index + 1);
        int hasThird = getNum(0, 1);
        String third = "";
        if (hasThird == 1) {
            index = getNum(0, length - 1);
            third = str.substring(index, index + 1);
        }
        return first + second + third;
    }


    /**
     * 返回手机号码
     */

    private String getTel() {
        final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }


    private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回Email
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail() {
        int length = getNum(1, 36);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
        return sb.toString();
    }


    /**
     * 随机生成身份证18位号码工具类
     * <p>
     * 居民身份证号码是根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，
     * 由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。居民身份证是国家法定的证明公民个人身份的有效证件。
     * 　身份证号码构成：
     * 　　1 地址码
     * 　　（身份证前六位）表示编码对象常住户口所在县(市、镇、区)的行政区划代码。
     * 　　2 生日期码
     * 　　（身份证第七位到第十四位）表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。例如：1981年05月11日就用19810511表示。
     * 　　3 顺序码
     * 　　（身份证第十五位到十七位）为同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。其中第十七位奇数分给男性，偶数分给女性。（随机生成）
     * 　　4 校验码
     * 　　（身份证最后一位）是根据前面十七位数字码由号码编制单位按统一的公式计算出来的，
     * 如果某人的尾号是0-9，都不会出现X，但如果尾号是10，那么就得用X来代替，因为如果用10做尾号，那么此人的身份证就变成了19位，而19位的号码违反了国家标准，
     * 并且我国的计算机应用系统也不承认19位的身份证号码。Ⅹ是罗马数字的10，用X来代替10，可以保证公民的身份证符合国家标准
     * 代码源于网络 由kingYiFan整理  create2019/05/24
     */


    /**
     * 生成随机地区编码
     *
     * @return
     */
    private int randomAreaCode() {
        int index = (int) (Math.random() * areaCode.size());
        Collection<Integer> values = areaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i < index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;
    }

    /**
     * 随机出生日期
     */
    private String randomBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 60) + 1950);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        return builder.toString();
    }

    /**
     * 随机产生3位
     *
     * @return
     */
    private String randomCode() {
        int code = (int) (Math.random() * 1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    /**
     * 生成最后一位数字
     *
     * @param chars
     * @return
     */
    private char calcTrailingNumber(char[] chars) {
        if (chars.length < 17) {
            return ' ';
        }
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        return r[result % 11];
    }

    /**
     * 生成身份证
     *
     * @return
     */
    private String generateIdCard() {
        StringBuilder generater = new StringBuilder();
        generater.append(this.randomAreaCode());
        generater.append(this.randomBirthday());
        generater.append(this.randomCode());
        generater.append(this.calcTrailingNumber(generater.toString().toCharArray()));
        return generater.toString();
    }

    //创建一个map存放编码
    static final Map<String, Integer> areaCode = new HashMap<String, Integer>();

    static {
        areaCode.put("北京市", 110000);
        areaCode.put("江都市", 321088);
        areaCode.put("镇江市", 321100);
        areaCode.put("市辖区", 321101);
        areaCode.put("京口区", 321102);
        areaCode.put("润州区", 321111);
        areaCode.put("丹徒区", 321112);
        areaCode.put("丹阳市", 321181);
        areaCode.put("扬中市", 321182);
        areaCode.put("句容市", 321183);
        areaCode.put("泰州市", 321200);
        areaCode.put("市辖区", 321201);
        areaCode.put("海陵区", 321202);
        areaCode.put("高港区", 321203);
        areaCode.put("兴化市", 321281);
        areaCode.put("靖江市", 321282);
        areaCode.put("泰兴市", 321283);
        areaCode.put("姜堰市", 321284);
        areaCode.put("宿迁市", 321300);
        areaCode.put("市辖区", 321301);
        areaCode.put("万宁市", 469006);
        areaCode.put("东方市", 469007);
        areaCode.put("定安县", 469021);
        areaCode.put("屯昌县", 469022);
        areaCode.put("澄迈县", 469023);
        areaCode.put("临高县", 469024);
        areaCode.put("白沙黎族自治县", 469025);
        areaCode.put("昌江黎族自治县", 469026);
        areaCode.put("乐东黎族自治县", 469027);
        areaCode.put("陵水黎族自治县", 469028);
        areaCode.put("保亭黎族苗族自治县", 469029);
        areaCode.put("琼中黎族苗族自治县", 469030);
        areaCode.put("西沙群岛", 469031);
        areaCode.put("南沙群岛", 469032);
        areaCode.put("中沙群岛的岛礁及其海域", 469033);
        areaCode.put("重庆市", 500000);
        areaCode.put("市辖区", 500100);
        areaCode.put("万州区", 500101);
        areaCode.put("涪陵区", 500102);
        areaCode.put("渝中区", 500103);
        areaCode.put("大渡口区", 500104);
        areaCode.put("江北区", 500105);
        areaCode.put("沙坪坝区", 500106);
        areaCode.put("九龙坡区", 500107);
        areaCode.put("南岸区", 500108);
        areaCode.put("北碚区", 500109);
        areaCode.put("万盛区", 500110);
        areaCode.put("双桥区", 500111);
        areaCode.put("渝北区", 500112);
        areaCode.put("巴南区", 500113);
        areaCode.put("澳门特别行政区", 820000);

    }


    /**
     * 身份证获取性别和年龄
     *
     * @param card
     * @return
     * @throws Exception
     */
    private Map<String, Object> identityCard(String card) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (card.length() == 18) {
            // 得到年份
            String year = card.substring(6).substring(0, 4);
            // 得到月份
            String month = card.substring(10).substring(0, 2);
            //得到日
            //String day=CardCode.substring(12).substring(0,2);
            String sex;
            // 判断性别
            if (Integer.parseInt(card.substring(16).substring(0, 1)) % 2 == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
            // 得到当前的系统时间
            Date date = new Date();
            // 当前年份
            String currentYear = format.format(date).substring(0, 4);
            // 月份
            String currentMonth = format.format(date).substring(5, 7);
            //String currentdDay=format.format(date).substring(8,10);
            int age = 0;
            // 当前月份大于用户出身的月份表示已过生日
            if (Integer.parseInt(month) <= Integer.parseInt(currentMonth)) {
                age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
            } else {
                // 当前用户还没过生日
                age = Integer.parseInt(currentYear) - Integer.parseInt(year);
            }
            map.put("sex", sex);
            map.put("age", age);
            return map;
        }

        //年份
        String year = "19" + card.substring(6, 8);
        //月份
        String yue = card.substring(8, 10);
        //日
        //String day=card.substring(10, 12);
        String sex;
        if (Integer.parseInt(card.substring(14, 15)) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        // 得到当前的系统时间
        Date date = new Date();
        //当前年份
        String currentYear = format.format(date).substring(0, 4);
        //月份
        String currentMonth = format.format(date).substring(5, 7);
        //String fday=format.format(date).substring(8,10);
        int age = 0;
        //当前月份大于用户出身的月份表示已过生日
        if (Integer.parseInt(yue) <= Integer.parseInt(currentMonth)) {
            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
        } else {
            // 当前用户还没过生日
            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }


    private ProvinceInfo getProCity() {

        String[] province = {"河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省", "青海省", "台湾省",};
        String[] city = {"安康市", "安庆市", "安顺市", "安阳市", "鞍山市", "巴彦淖尔市", "巴中市", "白城市", "白山市", "白银市", "百色市", "蚌埠市", "包头市", "宝鸡市", "保定市", "保山市", "北海市", "本溪市", "滨州市", "沧州市", "昌都地区", "长春市", "长沙市", "长治市", "常德市", "常州市", "巢湖市", "朝阳市", "潮州市", "郴州市", "成都市", "承德市", "池州市", "赤峰市", "崇左市", "滁州市", "达州市", "大连市", "大庆市", "大同市", "丹东市", "德阳市", "德州市", "定西市", "东莞市", "东营市", "鄂尔多斯市", "鄂州市", "防城港市", "佛山市", "福州市", "抚顺市", "抚州市", "阜新市", "阜阳市", "甘南州", "赣州市", "固原市", "广安市", "广元市", "广州市", "贵港市", "贵阳市", "桂林市", "哈尔滨市", "哈密地区", "海北藏族自治州", "海东地区", "海口市", "邯郸市", "汉中市", "杭州市", "毫州市", "合肥市", "河池市", "河源市", "菏泽市", "贺州市", "鹤壁市", "鹤岗市", "黑河市", "衡水市", "衡阳市", "呼和浩特市", "呼伦贝尔市", "湖州市", "葫芦岛市", "怀化市", "淮安市", "淮北市", "淮南市", "黄冈市", "黄山市", "黄石市", "惠州市", "鸡西市", "吉安市", "吉林市", "济南市", "济宁市", "佳木斯市", "嘉兴市", "嘉峪关市", "江门市", "焦作市", "揭阳市", "金昌市", "金华市", "锦州市", "晋城市", "晋中市", "荆门市", "荆州市", "景德镇市", "九江市", "酒泉市", "开封市", "克拉玛依市", "昆明市", "拉萨市", "来宾市", "莱芜市", "兰州市", "廊坊市", "乐山市", "丽江市", "丽水市", "连云港市", "辽阳市", "辽源市", "聊城市", "临沧市", "临汾市", "临沂市", "柳州市", "六安市", "六盘水市", "龙岩市", "陇南市", "娄底市", "泸州市", "吕梁市", "洛阳市", "漯河市", "马鞍山市", "茂名市", "眉山市", "梅州市", "绵阳市", "牡丹江市", "内江市", "南昌市", "南充市", "南京市", "南宁市", "南平市", "南通市", "南阳市", "宁波市", "宁德市", "攀枝花市", "盘锦市", "平顶山市", "平凉市", "萍乡市", "莆田市", "濮阳市", "普洱市", "七台河市", "齐齐哈尔市", "钦州市", "秦皇岛市", "青岛市", "清远市", "庆阳市", "曲靖市", "衢州市", "泉州市", "日照市", "三门峡市", "三明市", "三亚市", "汕头市", "汕尾市", "商洛市", "商丘市", "上饶市", "韶关市", "邵阳市", "绍兴市", "深圳市", "沈阳市", "十堰市", "石家庄市", "石嘴山市", "双鸭山市", "朔州市", "四平市", "松原市", "苏州市", "宿迁市", "宿州市", "绥化市", "随州市", "遂宁市", "台州市", "太原市", "泰安市", "泰州市", "唐山市", "天水市", "铁岭市", "通化市", "通辽市", "铜川市", "铜陵市", "铜仁市", "吐鲁番地区", "威海市", "潍坊市", "渭南市", "温州市", "乌海市", "乌兰察布市", "乌鲁木齐市", "无锡市", "吴忠市", "芜湖市", "梧州市", "武汉市", "武威市", "西安市", "西宁市", "锡林郭勒盟", "厦门市", "咸宁市", "咸阳市", "湘潭市", "襄樊市", "孝感市", "忻州市", "新乡市", "新余市", "信阳市", "兴安盟", "邢台市", "徐州市", "许昌市", "宣城市", "雅安市", "烟台市", "延安市", "盐城市", "扬州市", "阳江市", "阳泉市", "伊春市", "伊犁哈萨克自治州", "宜宾市", "宜昌市", "宜春市", "益阳市", "银川市", "鹰潭市", "营口市", "永州市", "榆林市", "玉林市", "玉溪市", "岳阳市", "云浮市", "运城市", "枣庄市", "湛江市", "张家界市", "张家口市", "张掖市", "漳州市", "昭通市", "肇庆市", "镇江市", "郑州市", "中山市", "中卫市", "舟山市", "周口市", "株洲市", "珠海市", "驻马店市", "资阳市", "淄博市", "自贡市", "遵义市",};
        String[] area = {"伊春区", "带岭区", "南岔区", "金山屯区", "西林区", "美溪区", "乌马河区", "翠峦区", "友好区", "新青区", "上甘岭区", "五营区", "红星区", "汤旺河区", "乌伊岭区", "榆次区"};
        String[] road = {"爱国路", "安边路", "安波路", "安德路", "安汾路", "安福路", "安国路", "安化路", "安澜路", "安龙路", "安仁路", "安顺路", "安亭路", "安图路", "安业路", "安义路", "安远路", "鞍山路", "鞍山支路", "澳门路", "八一路", "巴林路", "白城路", "白城南路", "白渡路", "白渡桥", "白兰路", "白水路", "白玉路", "百安路（方泰镇）", "百官街", "百花街", "百色路", "板泉路", "半淞园路", "包头路", "包头南路", "宝安公路", "宝安路", "宝昌路", "宝联路", "宝林路", "宝祁路", "宝山路", "宝通路", "宝杨路", "宝源路", "保德路", "保定路", "保屯路", "保屯路", "北艾路",};
        String[] home = {"金色家园", "耀江花园", "阳光翠竹苑", "东新大厦", "溢盈河畔别墅", "真新六街坊", "和亭佳苑", "协通公寓", "博泰新苑", "菊园五街坊", "住友嘉馨名园", "复华城市花园", "爱里舍花园"};

        Random random = new Random();
        int randomProvinceNum = random.nextInt(province.length);
        int randomCityNum = random.nextInt(city.length);
        int randomAreaNum = random.nextInt(area.length);
        int randomRoadNum = random.nextInt(road.length);
        int randomHomeNum = random.nextInt(home.length);
        int num = random.nextInt(200);

        return ProvinceInfo.builder()
                .province(province[randomProvinceNum])
                .city(city[randomAreaNum])
                .areaRold(area[randomAreaNum] + road[randomRoadNum] + num + "号" + home[randomHomeNum])
                .build();


    }


    @Data
    @Builder
    static class ProvinceInfo {
        private String province;
        private String city;
        private String areaRold;
    }


    @Override
    public AutoTaskDataEntity call() throws Exception {
        return createDateInfo();
    }


    public AutoCreateDataServiceTask() {

    }
}
