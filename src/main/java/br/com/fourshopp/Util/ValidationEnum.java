package br.com.fourshopp.Util;

import java.util.regex.Pattern;

public enum ValidationEnum {
    CPF("^([0-9]{3}\\.?){3}-?[0-9]{2}$"),
    CELLPHONE("^[0-9]{2}[7-9]{1}[0-9]{8}$"),
    EMAIL("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$"),
    DATE("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0" +
            "[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32" +
            "|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)"),
    PASSWORD("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$");

    private String key;

    ValidationEnum(String key) {
        this.key = key;
    }


    public Pattern getKey() {
        return Pattern.compile(key);

    }
}
