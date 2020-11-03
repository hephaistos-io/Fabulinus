package ch.hephaistos.fabulinus.annotations.post;

public interface PostValidatorStrategy {

    public boolean validate(String value);
}
