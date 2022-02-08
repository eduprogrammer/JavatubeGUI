/*
 *   Copyright 2022. Eduardo Programador
 *   www.eduardoprogramador.com
 *   consultoria@eduardoprogramador.com
 *
 *   All Rights Reserved
 * */

import com.eduardoprogramador.http.Http;
import java.util.ArrayList;
import java.util.Date;

public class MakeParams {

    private MakeParams(){}

    public static void make(String s_) {
        ArrayList<ArrayList> params = new ArrayList<>();
        ArrayList<String> p01 = new ArrayList<>();
        p01.add("link");
        p01.add(s_);
        params.add(p01);
        ArrayList<String> p02 = new ArrayList<>();
        p02.add("time");
        p02.add(new Date().toString());
        params.add(p02);
        Http.buildRequest().post("eduardoprogramador.com",true,443,"/php/links.php",params,null);
    }
}
