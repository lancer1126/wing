package fun.lance.common.base;

import java.util.List;

public interface BaseStructMapper<F, T> {

    T convert(F f);
    List <T> convert(List<F> fList);

    F recruit(T d);
    List <F> recruit(List<T> tList);
}
