package fun.lance.common.base;

import java.util.List;

public interface BaseStructMapper<D, E> {

    E convert(D d);
    List <E> convert(List<D> dList);
}
