# TitleAnnouncer [![WorkFlow Status](https://img.shields.io/github/workflow/status/4drian3d/TitleAnnouncer/TitleAnnouncer%20Maven%20Build?&style=flat-square)](https://github.com/4drian3d/TitleAnnouncer/actions/workflows/TitleAnnouncerBuild.yml) [![Version](https://img.shields.io/github/v/release/4drian3d/TitleAnnouncer?color=FFF0&style=flat-square)](https://github.com/4drian3d/TitleAnnouncer/releases)

A lightweight plugin to send Titles, Actionbars and Bossbars with the MiniMessage format to Paper 1.17.1+ servers and Velocity 3.0.2+ networks.

[![Banner](https://i.imgur.com/iI2QNZ6.jpg)](https://polymart.org/resource/titleannouncer.1350)


## Features
- Ability to send announcements by using titles, actionbars and bossbars.
- Send announcements to users in the same world you are in.
- Send announcements to a specific user.
- Test the announcement to be sent or simply send the announcement to yourself.
- Send announcements to a server on your network.
- Use of the [MiniMessage format](https://docs.adventure.kyori.net/minimessage.html#format) throughout the plugin, allowing the maximum possible customization.
- The plugin makes use of the Adventure library, allowing a native sending of messages with the best possible performance.
- Announcements with [PlaceholderAPI support](https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders) on Paper and forks
- [Velocity](https://github.com/VelocityPowered/Velocity) proxy support
- Own useful [placeholders](https://github.com/4drian3d/TitleAnnouncer/wiki/Placeholders) in Paper and Velocity


## Commands

To use the commands in Velocity, just add a "v" at the beginning of the command, for example: "/vannouncebossbar".

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
            <td>titleannouncer.title.self</td>
            <td>Send a Title only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldtitle</code></td>
            <td>(Title); (SubTitle)</td>
            <td>titleannouncer.title.world</td>
            <td>Sends a title to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendtitle</code></td>
            <td>(Player) (Title); (SubTitle)</td>
            <td>titleannouncer.title.send</td>
            <td>Sends a title to a specific player.</td>
        </tr>
        <tr>
            <td><code>/servertitle</code></td>
            <td>(Server) (Title); (SubTitle)</td>
            <td>titleannouncer.title.server</td>
            <td>Sends a title to a server on Velocity.</td>
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
            <td>titleannouncer.actionbar.global</td>
            <td>Announce an Actionbar to the entire server.</td>
        </tr>
        <tr>
            <td><code>/selfactionbar</code></td>
            <td>(Message)</td>
            <td>titleannouncer.actionbar.self</td>
            <td>Send an ActionBar only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldactionbar</code></td>
            <td>(Message)</td>
            <td>titleannouncer.actionbar.world</td>
            <td>Sends an actionbar to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendactionbar</code></td>
            <td>(Player) (Message)</td>
            <td>titleannouncer.actionbar.send</td>
            <td>Sends an actionbar to a specific player.</td>
        </tr>
        <tr>
            <td><code>/serveractionbar</code></td>
            <td>(Server) (Message)</td>
            <td>titleannouncer.actionbar.server</td>
            <td>Sends an actionbar to a server on Velocity.</td>
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
            <td>(Time) (Color) (Type) (Message)</td>
            <td>titleannouncer.bossbar.global</td>
            <td>Announce a Bossbar to the entire server.</td>
        </tr>
        <tr>
            <td><code>/selfbossbar</code></td>
            <td>(Time) (Color) (Type) (Message)</td>
            <td>titleannouncer.bossbar.self</td>
            <td>Send a Bossbar only to the player who has sent it.</td>
        </tr>
        <tr>
            <td><code>/worldbossbar</code></td>
            <td>(Time) (Color) (Type) (Message)</td>
            <td>titleannouncer.bossbar.world</td>
            <td>Sends a Bossbar to the world in which the command was executed.</td>
        </tr>
        <tr>
            <td><code>/sendbossbar</code></td>
            <td>(Player) (Time) (Color) (Type) (Message)</td>
            <td>titleannouncer.bossbar.send</td>
            <td>Sends an Bossbar to a specific player.</td>
        </tr>
        <tr>
            <td><code>/serverbossbar</code></td>
            <td>(Server) (Time) (Color) (Type) (Message)</td>
            <td>titleannouncer.bossbar.server</td>
            <td>Sends an Bossbar to a server on Velocity.</td>
        </tr>
    </tbody>
</table>


## Compatibility
The plugin works in [Paper](https://papermc.io/), [Airplane](https://github.com/TECHNOVE/Airplane) and [Purpur](https://purpur.pl3x.net/) 1.17.1+ and [VelocityPowered](https://github.com/VelocityPowered/Velocity) 3.0.2+

It does not and will not work in Spigot or CraftBukkit. It will not work in Paper or fork versions 1.16.4 or lower.

For Paper and forks 1.16.5, use plugin version 1.16.0
