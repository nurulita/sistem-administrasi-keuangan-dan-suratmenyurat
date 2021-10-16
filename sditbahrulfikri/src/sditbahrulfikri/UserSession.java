/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sditbahrulfikri;

/**
 *
 * @author nurul
 */
public class UserSession {
    private static int u_id;
    private static String u_username;
    private static String u_password;
    private static String u_nama;
    private static String u_alamat;
    private static String u_telepon;
    private static String u_email;
    
    public static int getU_id() {
        return u_id;
    }

    public static void setU_id(int u_id) {
        UserSession.u_id = u_id;
    }

    public static String getU_username() {
        return u_username;
    }

    public static void setU_username(String u_username) {
        UserSession.u_username = u_username;
    }

    public static String getU_password() {
        return u_password;
    }

    public static void setU_password(String u_password) {
        UserSession.u_password = u_password;
    }

    public static String getU_nama() {
        return u_nama;
    }

    public static void setU_nama(String u_nama) {
        UserSession.u_nama = u_nama;
    }

    public static String getU_alamat() {
        return u_alamat;
    }

    public static void setU_alamat(String u_alamat) {
        UserSession.u_alamat = u_alamat;
    }

    public static String getU_telepon() {
        return u_telepon;
    }

    public static void setU_telepon(String u_telepon) {
        UserSession.u_telepon = u_telepon;
    }

    public static String getU_email() {
        return u_email;
    }

    public static void setU_email(String u_email) {
        UserSession.u_email = u_email;
    }
    
}
