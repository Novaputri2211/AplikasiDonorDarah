package com.example.aplikasidonordarah.Penerima;

import java.util.List;

public class ListPendonor {

    /**
     * success : 1
     * status : 200
     * message : Data Ada
     * DATA : [{"id_pendonor":"2","id_user":"0","nama":"Putri","tmpt_lahir":"Bandung","tanggal_lahir":"2000-04-15","umur":"27","jekel":"P","alamat":"Medan","lat":"7899","lng":"678","no_hp":"08205363","gol_darah":"A","bb":"55","tensi":"135/60","kadar_hb":"45","tanggal_donor":"2021-08-26","jlh_kantong_pendonor":"8"},{"id_pendonor":"8","id_user":"5","nama":"Nova Putri Octaviani","tmpt_lahir":"Jakarta","tanggal_lahir":"2021-08-17","umur":"21","jekel":"Perempuan","alamat":"Jl. Dr. Moh. Hatta No.8b, RT.01/RW.02, Kapala Koto, Kec. Pauh, Kota Padang, Sumatera Barat 25163, Indonesia","lat":"-0.9258313","lng":"100.4350408","no_hp":"082370456549","gol_darah":"A","bb":"43","tensi":"120/70","kadar_hb":"35%","tanggal_donor":"2021-09-07","jlh_kantong_pendonor":"0"},{"id_pendonor":"1","id_user":"0","nama":"Nova Putri","tmpt_lahir":"Jakarta","tanggal_lahir":"2000-10-11","umur":"21","jekel":"P","alamat":"Medan","lat":"8387992","lng":"6373828","no_hp":"082356748","gol_darah":"AB","bb":"43","tensi":"130/70","kadar_hb":"60","tanggal_donor":"2021-08-25","jlh_kantong_pendonor":"6"},{"id_pendonor":"7","id_user":"5","nama":"Nova Putri Oc","tmpt_lahir":"Jakarta","tanggal_lahir":"2021-08-09","umur":"20","jekel":"Perempuan","alamat":"3FP8+C9P, Limau Manis, Pauh, Padang City, West Sumatra 25175, Indonesia","lat":"-0.9136104","lng":"100.4659182","no_hp":"082370456579","gol_darah":"AB","bb":"43","tensi":"135/90","kadar_hb":"35%","tanggal_donor":"2021-09-07","jlh_kantong_pendonor":"2"},{"id_pendonor":"5","id_user":"0","nama":"nope","tmpt_lahir":"jekardah","tanggal_lahir":"2021-09-09","umur":"20","jekel":"Perem","alamat":"Jl. Dr. Moh. Hatta No.8b, RT.01/RW.02, Kapala Koto, Kec. Pauh, Kota Padang, Sumatera Barat 25163, Indonesia","lat":"-0.9258311","lng":"100.435053","no_hp":"082370456579","gol_darah":"O","bb":"43","tensi":"130/20","kadar_hb":"35%","tanggal_donor":"2021-09-20","jlh_kantong_pendonor":"0"}]
     */

    private int success;
    private int status;
    private String message;
    /**
     * id_pendonor : 2
     * id_user : 0
     * nama : Putri
     * tmpt_lahir : Bandung
     * tanggal_lahir : 2000-04-15
     * umur : 27
     * jekel : P
     * alamat : Medan
     * lat : 7899
     * lng : 678
     * no_hp : 08205363
     * gol_darah : A
     * bb : 55
     * tensi : 135/60
     * kadar_hb : 45
     * tanggal_donor : 2021-08-26
     * jlh_kantong_pendonor : 8
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
        private String id_pendonor;
        private String id_user;
        private String nama;
        private String tmpt_lahir;
        private String tanggal_lahir;
        private String umur;
        private String jekel;
        private String alamat;
        private String lat;
        private String lng;
        private String no_hp;
        private String gol_darah;
        private String bb;
        private String tensi;
        private String kadar_hb;
        private String tanggal_donor;
        private String jlh_kantong_pendonor;

        public String getId_pendonor() {
            return id_pendonor;
        }

        public void setId_pendonor(String id_pendonor) {
            this.id_pendonor = id_pendonor;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTmpt_lahir() {
            return tmpt_lahir;
        }

        public void setTmpt_lahir(String tmpt_lahir) {
            this.tmpt_lahir = tmpt_lahir;
        }

        public String getTanggal_lahir() {
            return tanggal_lahir;
        }

        public void setTanggal_lahir(String tanggal_lahir) {
            this.tanggal_lahir = tanggal_lahir;
        }

        public String getUmur() {
            return umur;
        }

        public void setUmur(String umur) {
            this.umur = umur;
        }

        public String getJekel() {
            return jekel;
        }

        public void setJekel(String jekel) {
            this.jekel = jekel;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getNo_hp() {
            return no_hp;
        }

        public void setNo_hp(String no_hp) {
            this.no_hp = no_hp;
        }

        public String getGol_darah() {
            return gol_darah;
        }

        public void setGol_darah(String gol_darah) {
            this.gol_darah = gol_darah;
        }

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }

        public String getTensi() {
            return tensi;
        }

        public void setTensi(String tensi) {
            this.tensi = tensi;
        }

        public String getKadar_hb() {
            return kadar_hb;
        }

        public void setKadar_hb(String kadar_hb) {
            this.kadar_hb = kadar_hb;
        }

        public String getTanggal_donor() {
            return tanggal_donor;
        }

        public void setTanggal_donor(String tanggal_donor) {
            this.tanggal_donor = tanggal_donor;
        }

        public String getJlh_kantong_pendonor() {
            return jlh_kantong_pendonor;
        }

        public void setJlh_kantong_pendonor(String jlh_kantong_pendonor) {
            this.jlh_kantong_pendonor = jlh_kantong_pendonor;
        }
    }
}
