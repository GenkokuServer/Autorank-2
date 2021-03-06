package me.armar.plugins.autorank.pathbuilder.requirement;

import me.armar.plugins.autorank.language.Lang;
import me.staartvin.plugins.pluginlibrary.Library;
import me.staartvin.plugins.pluginlibrary.hooks.PlayerPointsHook;
import org.bukkit.entity.Player;

public class PlayerPointsPointsRequirement extends AbstractRequirement {

    private PlayerPointsHook handler = null;
    private int requiredPoints = -1;

    @Override
    public String getDescription() {
        return Lang.PLAYERPOINTS_POINTS_REQUIREMENT.getConfigValue(requiredPoints);
    }

    @Override
    public String getProgress(final Player player) {
        return handler.getPlayerPoints(player.getUniqueId()) + "/" + requiredPoints;
    }

    @Override
    public boolean meetsRequirement(final Player player) {

        if (!handler.isAvailable())
            return false;

        return handler.getPlayerPoints(player.getUniqueId()) >= requiredPoints;
    }

    @Override
    public boolean setOptions(final String[] options) {

        // Add dependency
        addDependency(Library.PLAYERPOINTS);

        handler = (PlayerPointsHook) this.getDependencyManager().getLibraryHook(Library.PLAYERPOINTS);

        if (options.length > 0) {
            try {
                requiredPoints = Integer.parseInt(options[0]);
            } catch (NumberFormatException e) {
                this.registerWarningMessage("An invalid number is provided");
                return false;
            }
        }

        if (requiredPoints < 0) {
            this.registerWarningMessage("No number is provided or smaller than 0.");
            return false;
        }

        return true;
    }
}
