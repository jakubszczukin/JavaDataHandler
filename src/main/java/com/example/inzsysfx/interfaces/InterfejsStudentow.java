package com.example.inzsysfx.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface InterfejsStudentow {
    @WebMethod
    String dajStudentaNazwisko(String nazwisko);
    @WebMethod String dajNajlepszegoStudenta();
}
