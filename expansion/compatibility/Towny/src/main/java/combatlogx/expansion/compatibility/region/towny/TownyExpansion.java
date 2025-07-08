package combatlogx.expansion.compatibility.region.towny;

import org.jetbrains.annotations.NotNull;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.region.RegionExpansion;
import com.github.sirblobman.combatlogx.api.expansion.region.RegionHandler;

import combatlogx.expansion.compatibility.region.towny.listener.ListenerPrison;

public final class TownyExpansion extends RegionExpansion {
    private final TownyConfiguration configuration;
    private RegionHandler<?> regionHandler;

    public TownyExpansion(@NotNull ICombatLogX plugin) {
        super(plugin);
        this.configuration = new TownyConfiguration();
        this.regionHandler = null;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("towny.yml");
    }

    @Override
    public boolean checkDependencies() {
        return checkDependency("Towny", true, "0.101");
    }

    @Override
    public void afterEnable() {
        checkDependency("FlagWar", true);
        new ListenerPrison(this).register();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("towny.yml");
        getTownyConfiguration().load(configurationManager.get("towny.yml"));
    }

    @Override
    public @NotNull RegionHandler<?> getRegionHandler() {
        if (this.regionHandler == null) {
            this.regionHandler = new RegionHandlerTowny(this);
        }

        return this.regionHandler;
    }

    public @NotNull TownyConfiguration getTownyConfiguration() {
        return this.configuration;
    }
}
