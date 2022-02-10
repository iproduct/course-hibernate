package course.hibernate.entity;

public enum Gender {

    MALE( 'M' ),
    FEMALE( 'F' ),
    UNSPECIFIED( 'U' );

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public static Gender fromCode(char code) {
        if ( code == 'M' || code == 'm' ) {
            return MALE;
        }
        if ( code == 'F' || code == 'f' ) {
            return FEMALE;
        }
        if ( code == 'U' || code == 'u' ) {
            return UNSPECIFIED;
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!"
        );
    }

    public char getCode() {
        return code;
    }
}
