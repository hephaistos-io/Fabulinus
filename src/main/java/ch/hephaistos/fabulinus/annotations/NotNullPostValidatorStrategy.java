package ch.hephaistos.fabulinus.annotations;

public class NotNullPostValidatorStrategy implements PostValidatorStrategy{

    /**
     * Returns true if value is not null
     * @param value
     * @return will return value != null
     */
    @Override
    public boolean validate(String value) {
        return value != null;
    }
}
