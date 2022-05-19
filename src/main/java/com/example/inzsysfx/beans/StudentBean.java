package com.example.inzsysfx.beans;

import com.example.inzsysfx.interfaces.InterfejsStudentow;

import javax.jws.WebService;

@WebService(endpointInterface = "com.example.inzsysfx.interfaces.InterfejsStudentow")
public class StudentBean implements InterfejsStudentow {

    Object[][] studenciBD = {{1, "Student 1", 5.0}, {2, "Student 2", 5.0}, {3, "Student 3", 5.0}};

    private String dajStudenta(int idnt){
        String s = "";
        for(int i = 0; i < studenciBD.length; i++){
            s += studenciBD[idnt][i] + " ";
        }
        return s + "\n";
    }

    @Override
    public String dajStudentaNazwisko(String nazwisko) {
        for(int i = 0; i < studenciBD.length; i++){
            if(studenciBD[i][1].equals(nazwisko))
                return dajStudenta(i);
        }
        return null;
    }

    @Override
    public String dajNajlepszegoStudenta() {
        int naj = 0;
        return "abcabc";
    }
}
