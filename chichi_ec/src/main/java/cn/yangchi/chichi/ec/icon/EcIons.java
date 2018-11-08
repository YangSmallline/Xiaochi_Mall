package cn.yangchi.chichi.ec.icon;

import com.joanzapata.iconify.Icon;

public enum  EcIons implements Icon {
    icon2('\ue6e1'),
    icon3('\ue524')
    ;

    private char character;

    EcIons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace("_","-");
    }

    @Override
    public char character() {
        return character;
    }
}
