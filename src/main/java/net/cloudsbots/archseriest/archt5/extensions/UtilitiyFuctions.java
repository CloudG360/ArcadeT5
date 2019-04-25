package net.cloudsbots.archseriest.archt5.extensions;

import net.cloudsbots.archseriest.archt5.components.UtilityFunctions;

import java.util.Map;

/**
 * <h2>WARNING: Updated preview system.</h2>
 *
 * This system is newer than what is already present in the code, hence why it's marked as a Development
 * preview system. If it hasn't already been merged into the ArchT5 development branch, it's likely that
 * it will be sometime in the future depending on if the changes are API breaking or just fixes + optimizations.
 *
 * Version Aim: When it's added, it's added.
 *
 */
public class UtilitiyFuctions extends UtilityFunctions {

    //TODO: Get "Casual" chat function looking for main channel

    /**
     * Merges the entries of two maps together, overriding any duplicates from the merging map
     * to the original
     *
     * @param original - The original map.
     * @param merge - The map to merge with it (Overrides entries in orignal if duplicates)
     * @return - Returns the new merged map.
     */
    public static Map<String, Object> mergeMaps(Map<String, Object> original, Map<String, Object> merge){
        Map<String, Object> merged = original;
        for(String key:merge.keySet()){
            if(merged.containsKey(key)){
                merged.remove(key);
            }
            merge.put(key, merge.get(key));
        }
        return merged;
    }

}
