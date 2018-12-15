package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Filter f = new It("code", "=", "100");
        System.out.println(vistor(f));
        Supplier<String> s = () -> "100";
        Filter f2 = new It("code", ">=", s);
        System.out.println(vistor(f2));

        Filter f3 = new Or(f, f2);
        System.out.println(vistor(f3));
        Filter f4 = new And(f, f3);
        System.out.println(vistor(f4));

        Filter f5 = new And(f4, f3, f, f);
        System.out.println(vistor(f5));
    }

    public static String vistor(Filter filter) {
        if (filter instanceof It) {
            return vistor((It) filter);
        }
        if (filter instanceof Or) {
            return vistor((Or) filter);
        }
        if (filter instanceof And) {
            return vistor((And) filter);
        }
        return "";
    }

    public static String vistor(It it) {
        Object v = it.getValue();
        if (v instanceof Supplier) {
            v = ((Supplier) v).get();
        }
        return it.getCode() + " " + it.getOp() + " " + v;
    }

    public static String vistor(Or it) {
        return it.getItems().stream().map(_it -> "(" + vistor(_it) + ")").collect(Collectors.joining(" or "));
    }

    public static String vistor(And it) {
        return it.getItems().stream().map(_it -> "(" + vistor(_it) + ")").collect(Collectors.joining(" and "));
    }

    public static interface Filter {
    }

    public static class It<T> implements Filter {
        private final String code;
        private final T value;
        private final String op;

        public It(String code, String op, T value) {
            this.code = code;
            this.value = value;
            this.op = op;
        }

        public String getCode() {
            return code;
        }

        public T getValue() {
            return value;
        }

        public String getOp() {
            return op;
        }
    }

    public static class Comp implements Filter {
        private final List<Filter> items;

        public Comp(Filter a, Filter b, Filter... rest) {
            List<Filter> _items = new ArrayList<>();
            _items.add(a);
            _items.add(b);
            if (rest != null && rest.length > 0) {
                _items.addAll(Arrays.asList(rest));
            }
            items = _items;
        }

        public List<Filter> getItems() {
            return items;
        }
    }

    public static class Or extends Comp {
        public Or(Filter a, Filter b, Filter... rest) {
            super(a, b, rest);
        }
    }

    public static class And extends Comp {
        public And(Filter a, Filter b, Filter... rest) {
            super(a, b, rest);
        }
    }
}
