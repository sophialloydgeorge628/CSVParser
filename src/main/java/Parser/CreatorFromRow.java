package Parser;

import java.util.List;

/**
 * interface creatorFromRow, which takes a generic type parameter and contains method create.
 * creatorFromRow can be implemented across
 * @param <T>,
 */
public interface CreatorFromRow<T> {
  T create(List<String> row) throws FactoryFailureException;
}
