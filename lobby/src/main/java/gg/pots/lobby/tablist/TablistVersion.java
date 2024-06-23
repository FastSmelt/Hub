package gg.pots.lobby.tablist;

import io.github.nosequel.tab.shared.TabAdapter;
import io.github.nosequel.tab.v1_7_r4.v1_7_R4TabAdapter;
import io.github.nosequel.tab.v1_8_r3.v1_8_R3TabAdapter;

public enum TablistVersion {

    v1_8 {
        @Override
        public TabAdapter getAdapter() {
            return new v1_8_R3TabAdapter();
        }
    },
    v1_7 {
        @Override
        public TabAdapter getAdapter() {
            return new v1_7_R4TabAdapter();
        }
    };

    /**
     * Retrive the adapter for the current version.
     *
     * @return the adapter.
     */

    public abstract TabAdapter getAdapter();
}
