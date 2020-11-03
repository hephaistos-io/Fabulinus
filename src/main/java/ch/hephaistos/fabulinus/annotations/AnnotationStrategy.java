package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.util.List;

/**
 * This interface is used as a strategy pattern. Every annotation has to have one AnnotationAdapter, which
 * handles how the adapter works trough a variable with the given annotations.
 */
public interface AnnotationStrategy {

    List<Pair<String, ValueAdapter>> parseFields(Object object);

}
