## ArchT5 Extension Packages

This area of the plugin include packages being constantly worked on, aimed to be added to the full ArchT5 project at a later date, but are currently not finished. The extensions being used by this package are:

- TaskManager ("net.cloudsbots.archseriest.archt5.tasks")
  - Central task manager
  - ArchTasks
- UtilityFuctions (For 1.0.0)

### Task Manager:

Aimed to replace the current Command Manager and the Behavior Manager, the task manager centralizes all processes so they can be managed easily and be monitored by users. The current version of Task Manager used in this project only supports new process types and not the legacy Command & Behavior processes. As this plugin still uses the old API, it uses the old Command Manager and Behavior Manager. 

**Version Aim:** 1.0.0 (No planned build ID yet)
**Tags:**
 - `[!] BREAKING_CHANGES` @ net.cloudsbots.archseriest.archt5.tasks.ArchTask
   - This is in relation to how Commands and Behaviors will have to work differently due to a new-ish structure.
 - `[!] REMOVE_ON_DEV_MERGE`
   - Only applies for components in the archt5/dev folder
   
### UtilityFunctions:

Already in ArchT5, this just includes a small addition added via an extension. It's not breaking but it's just not been added yet and testing would be preferred first. You can basically ignore this as it's not a big package change.

**Version Aim:** Roughly Next Release (It's a "when I get round to it" deal)
**Tags:**
 - `None`
 
 
 
## Templates
 
Just a few templates for me to use in my documentation to mark extension packages.

#### New System Class (Fully Unimplemented in ArchT5)

```
/**
 * <h2>WARNING: Development preview system.</h2>
 *
 * This system is not implemented into the core API yet, hence why it's referred to as a
 * development preview system. This means that it's official but only available in select
 * packages.
 *
 * [Description of Class] 
 *
 * Version Goal: [Version]
 * [!] Tags
 */
```

#### Updated System Class (Already in ArchT5 but newer)

```
/**
 * <h2>WARNING: Updated preview system.</h2>
 *
 * This system is newer than what is already present in the code, hence why it's marked as a Development
 * preview system. If it hasn't already been merged into the ArchT5 development branch, it's likely that
 * it will be sometime in the future depending on if the changes are API breaking or just fixes + optimizations.
 * 
 * Version Aim: [Version]
 * [!] Tags
 */
```

#### Temporary Dev System Class (Will be removed for ArchT5)

```
/**
 * <h2>WARNING: Test Component for preview system.</h2>
 *
 * This system is purley for testing other Extension packages and will be removed once those packages get
 * full implementations in the development branch of ArchT5 with refined testing components. 
 *
 * Target Package: [Package ID]
 * Version Aim: [Version]
 * [!] Tags
 */
```