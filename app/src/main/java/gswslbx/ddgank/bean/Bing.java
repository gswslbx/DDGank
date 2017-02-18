package gswslbx.ddgank.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gswslbx on 2017/2/14.
 * 使用http://www.jsonschema2pojo.org/生成的实体
 */

public class Bing {

    @SerializedName("images")
    @Expose
    public List<Image> images = null;
    @SerializedName("tooltips")
    @Expose
    private Tooltips tooltips;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Tooltips getTooltips() {
        return tooltips;
    }

    public void setTooltips(Tooltips tooltips) {
        this.tooltips = tooltips;
    }


    public class Image {

        @SerializedName("startdate")
        @Expose
        private String startdate;
        @SerializedName("fullstartdate")
        @Expose
        private String fullstartdate;
        @SerializedName("enddate")
        @Expose
        private String enddate;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("urlbase")
        @Expose
        private String urlbase;
        @SerializedName("copyright")
        @Expose
        private String copyright;
        @SerializedName("copyrightlink")
        @Expose
        private String copyrightlink;
        @SerializedName("quiz")
        @Expose
        private String quiz;
        @SerializedName("wp")
        @Expose
        private Boolean wp;
        @SerializedName("hsh")
        @Expose
        private String hsh;
        @SerializedName("drk")
        @Expose
        private Integer drk;
        @SerializedName("top")
        @Expose
        private Integer top;
        @SerializedName("bot")
        @Expose
        private Integer bot;
        @SerializedName("hs")
        @Expose
        private List<Object> hs = null;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public Boolean getWp() {
            return wp;
        }

        public void setWp(Boolean wp) {
            this.wp = wp;
        }

        public String getHsh() {
            return hsh;
        }

        public void setHsh(String hsh) {
            this.hsh = hsh;
        }

        public Integer getDrk() {
            return drk;
        }

        public void setDrk(Integer drk) {
            this.drk = drk;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getBot() {
            return bot;
        }

        public void setBot(Integer bot) {
            this.bot = bot;
        }

        public List<Object> getHs() {
            return hs;
        }

        public void setHs(List<Object> hs) {
            this.hs = hs;
        }

    }


    public class Tooltips {

        @SerializedName("loading")
        @Expose
        private String loading;
        @SerializedName("previous")
        @Expose
        private String previous;
        @SerializedName("next")
        @Expose
        private String next;
        @SerializedName("walle")
        @Expose
        private String walle;
        @SerializedName("walls")
        @Expose
        private String walls;

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getWalle() {
            return walle;
        }

        public void setWalle(String walle) {
            this.walle = walle;
        }

        public String getWalls() {
            return walls;
        }

        public void setWalls(String walls) {
            this.walls = walls;
        }

    }
}
