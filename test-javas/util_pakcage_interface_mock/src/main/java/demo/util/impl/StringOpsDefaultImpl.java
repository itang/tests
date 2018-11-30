package demo.util.impl;

import demo.util.IStringOps;

public class StringOpsDefaultImpl implements IStringOps {
    @Override
    public boolean isStartsAndEndsWith(String str, String it) {
        if (str == null) {
            throw new IllegalArgumentException("str can't bu null");
        }
        if (it == null) {
            throw new IllegalArgumentException("it can't bu null");
        }
        if (str.isEmpty()) {
            return false;
        }

        return str.startsWith(it) && str.endsWith(it);
    }
}
