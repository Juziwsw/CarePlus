package cn.com.cjland.careplus.data;

/**
 * Created by Administrator on 2016/2/23.
 */
public class Question {
    public int qustionId;//问答的ID
    public String avatar;//问答人头像
    public String title;//问答人昵称
    public String date;//问答日期 "2016/02/03 17:50"
    public String summary;//病情描述
    public String respondNum;//回答人数
    public String heartNum;//关注人数
    public int photoNum;//照片数
    public String imagesUrlOne;//其中一张的图片地址
    public String imagesUrl[];//图片地址
    public boolean flag;//是否展开
    public boolean isHeart;//是否关注
    public boolean isHasPhoto;//是否有图片
}
