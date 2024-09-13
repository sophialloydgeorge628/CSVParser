package Parser;

import java.util.List;

public interface CreatorFromRowFloat<T> {
  T create(List<Float> row) throws FactoryFailureException;
}
