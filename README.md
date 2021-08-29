# TitleAnnouncer [![WorkFlow Status](https://img.shields.io/github/workflow/status/4drian3d/TitleAnnouncer/TitleAnnouncer%20Maven%20Build?&style=flat-square)](https://github.com/4drian3d/TitleAnnouncer/actions/workflows/TitleAnnouncerBuild.yml) [![Version](https://img.shields.io/github/v/release/4drian3d/TitleAnnouncer?color=FFF0&style=flat-square)](https://github.com/4drian3d/TitleAnnouncer/releases)

A lightweight plugin to send Titles, Actionbars and Bossbars with the MiniMessage format.

[![Banner](https://i.imgur.com/iI2QNZ6.jpg)](https://polymart.org/resource/titleannouncer.1350)


## Features
- Ability to send announcements by using titles, actionbars and bossbars.
- Send announcements to users in the same world you are in.
- Send announcements to a specific user.
- Test the announcement to be sent or simply send the announcement to yourself.
- Use of the [MiniMessage format](https://docs.adventure.kyori.net/minimessage.html#format) throughout the plugin, allowing the maximum possible customization.
- The plugin makes use of the Adventure library, allowing a native sending of messages with the best possible performance.


## Commands

### Main Command

<table>
    <thead>
    <tr>
        <th>Command</th>
        <th>Argument</th>
        <th>Permission</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>/announcer</code></td>
            <td>[help|reload]</td>
            <td>announcer.command</td>
            <td>Main Command.</td>
        </tr>
    </tbody>
</table>

### Title
<table>
    <thead>
    <tr>
        <th>Command</th>
        <th>Argument</th>
        <th>Permission</th>
        <th>Description</th>
    </tr>    
    </thead>
    <tbody>
        <tr>
            <td><code>/announcetitle</code></td>
            <td>(Title); (SubTitle)</td>
            <td>announcer.title.global</td>
            <td>Announces a Title to the entire server.</td>
        </tr>
        <tr>
            <td><code>/selftitle</code></td>
            <td>(Title); (SubTitle)</td>
            <td>announcer.title.self</td>
            <td>Send a Title only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldtitle</code></td>
            <td>(Title); (SubTitle)</td>
            <td>announcer.title.world</td>
            <td>Sends a title to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendtitle</code></td>
            <td>(Player) (Title); (SubTitle)</td>
            <td>announcer.title.send</td>
            <td>Sends a title to a specific player.</td>
        </tr>
    </tbody>
</table>


### Actionbar
<table>
    <thead>
    <tr>
        <th>Command</th>
        <th>Argument</th>
        <th>Permission</th>
        <th>Description</th>
    </tr>    
    </thead>
    <tbody>
        <tr>
            <td><code>/announceactionbar</code></td>
            <td>(Message)</td>
            <td>announcer.actionbar.global</td>
            <td>Announce an Actionbar to the entire server.</td>
        </tr>
        <tr>
            <td><code>/selfactionbar</code></td>
            <td>(Message)</td>
            <td>announcer.actionbar.self</td>
            <td>Send an ActionBar only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldactionbar</code></td>
            <td>(Message)</td>
            <td>announcer.actionbar.world</td>
            <td>Sends an actionbar to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendactionbar</code></td>
            <td>(Player) (Message)</td>
            <td>announcer.actionbar.send</td>
            <td>Sends an actionbar to a specific player.</td>
        </tr>
    </tbody>
</table>

### Bossbar
<table>
    <thead>
    <tr>
        <th>Command</th>
        <th>Argument</th>
        <th>Permission</th>
        <th>Description</th>
    </tr>    
    </thead>
    <tbody>
        <tr>
            <td><code>/announcebossbar</code></td>
            <td>(Interval) (Color) (Type) (Message)</td>
            <td>announcer.bossbar.global</td>
            <td>Announce a Bossbar to the entire server.</td>
        </tr>
        <tr>
            <td><code>/selfbossbar</code></td>
            <td>(Interval) (Color) (Type) (Message)</td>
            <td>announcer.bossbar.self</td>
            <td>Send a Bossbar only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldbossbar</code></td>
            <td>(Interval) (Color) (Type) (Message)</td>
            <td>announcer.bossbar.world</td>
            <td>Sends a Bossbar to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendbossbar</code></td>
            <td>(Player) (Interval) (Color) (Type) (Message)</td>
            <td>announcer.bossbar.send</td>
            <td>Sends an Bossbar to a specific player.</td>
        </tr>
    </tbody>
</table>


## Compatibility
The plugin works in [Paper](https://papermc.io/), [Tuinity](https://github.com/Tuinity/Tuinity), [Airplane](https://github.com/TECHNOVE/Airplane) and [Purpur](https://purpur.pl3x.net/) 1.16.5 - 1.17.1

It does not and will not work in Spigot or CraftBukkit. It will not work in Paper or fork versions 1.16.4 or lower.
