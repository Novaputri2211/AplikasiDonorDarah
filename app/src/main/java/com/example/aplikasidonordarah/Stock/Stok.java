package com.example.aplikasidonordarah.Stock;

import java.util.List;

public class Stok {


    /**
     * success : 1
     * status : 200
     * message : Data Ada
     * DATA : [{"gol_darah":"A","jlh_kantong_darah":"6"},{"gol_darah":"B","jlh_kantong_darah":"6"},{"gol_darah":"AB","jlh_kantong_darah":"0"},{"gol_darah":"O","jlh_kantong_darah":"15"}]
     */

    private int success;
    private int status;
    private String message;
    /**
     * gol_darah : A
     * jlh_kantong_darah : 6
     */

    private List<DATABean> DATA;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        private String gol_darah;
        private String jlh_kantong_darah;

        public String getGol_darah() {
            return gol_darah;
        }

        public void setGol_darah(String gol_darah) {
            this.gol_darah = gol_darah;
        }

        public String getJlh_kantong_darah() {
            return jlh_kantong_darah;
        }

        public void setJlh_kantong_darah(String jlh_kantong_darah) {
            this.jlh_kantong_darah = jlh_kantong_darah;
        }
    }
}
