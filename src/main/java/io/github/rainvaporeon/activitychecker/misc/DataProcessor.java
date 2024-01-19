package io.github.rainvaporeon.activitychecker.misc;

/**
 * A processor to streamline the processing of data
 * @param <T> the input type
 * @param <U> the process type
 * @param <R> the output type
 */
public abstract class DataProcessor<T, U, R> {
    /**
     * Pre-processes this given data
     * @param data the data
     * @return the processed data, ready for processing
     */
    protected abstract T preprocess(T data) throws DataProcessingException;

    /**
     * Processes the given data and transforms the type
     * @param data the data
     * @return the processed data
     */
    protected abstract U process(T data) throws DataProcessingException;

    /**
     * Processes the given data and finalizes the procedure
     * @param data the data
     * @return the final processed data
     */
    protected abstract R postprocess(U data) throws DataProcessingException;

    public R handle(T data) throws DataProcessingException {
        try {
            T t = preprocess(data);
            U u = process(t);
            return postprocess(u);
        } catch (Exception e) {
            if(e instanceof DataProcessingException) throw e;
            throw new DataProcessingException(e);
        }
    }
}
