package com.jcwhatever.bukkit.v1_8_R1.blocks;

import org.bukkit.block.Sign;

/**
 * Mock implementation of a {@link org.bukkit.block.BlockState} that
 * is extended by {@link org.bukkit.block.Sign} .
 */
public class MockSign extends MockBlockState implements Sign {

    String[] _lines = new String[] {
            "",
            "",
            "",
            ""
    };

    public MockSign(MockBlock mockBlock) {
        super(mockBlock);

        if (mockBlock._currentState instanceof MockSign) {
            _lines = ((MockSign) mockBlock._currentState)._lines.clone();
        }
    }

    @Override
    public String[] getLines() {
        return _lines;
    }

    @Override
    public String getLine(int i) throws IndexOutOfBoundsException {
        return _lines[i];
    }

    @Override
    public void setLine(int i, String s) throws IndexOutOfBoundsException {
        _lines[i] = s;
    }

    @Override
    public boolean update(boolean force) {
        if (super.update(force)) {
            MockBlock block = _world.getBlockAt(_x, _y, _z);
            block._currentState = this;
            return true;
        }
        return false;
    }
}
