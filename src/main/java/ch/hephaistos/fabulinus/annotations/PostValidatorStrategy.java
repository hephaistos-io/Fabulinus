package ch.hephaistos.fabulinus.annotations;

public interface PostValidatorStrategy {

    public boolean validate(String value);
}
