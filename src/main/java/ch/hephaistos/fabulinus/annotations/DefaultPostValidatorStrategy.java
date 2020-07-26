package ch.hephaistos.fabulinus.annotations;

public class DefaultPostValidatorStrategy implements PostValidatorStrategy{

    /**
     * Always returns true
     * @param value
     * @return will <b>always</b> return true
     */
    @Override
    public boolean validate(String value) {
        return true;
    }
}
