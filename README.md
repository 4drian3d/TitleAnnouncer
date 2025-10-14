# TitleAnnouncer

[![GitHub WorkFlow](https://img.shields.io/github/actions/workflow/status/4drian3d/TitleAnnouncer/TitleAnnouncerBuild.yml?logo=GitHub&style=flat-square)](https://github.com/4drian3d/TitleAnnouncer/actions)
![Latest Version](https://img.shields.io/github/v/release/4drian3d/TitleAnnouncer?style=flat-square)
[![Discord](https://img.shields.io/discord/899740810956910683?color=7289da&logo=Discord&label=Discord&style=flat-square)](https://discord.gg/5NMMzK5mAn)
![Modrinth Downloads](https://img.shields.io/modrinth/dt/d769vI4q?logo=Modrinth&style=flat-square)
![GitHub Downloads](https://img.shields.io/github/downloads/4drian3d/TitleAnnouncer/total?logo=GitHub&style=flat-square)

A lightweight plugin to send Titles, Actionbars, Bossbars and Chat Announces to Paper servers and Velocity networks.

## Features
- Ability to send announcements by using titles, actionbars, bossbars, and chat messages.
- Send announcements to users in the same world or server you are in.
- Send announcements to a specific user.
- Use of the [MiniMessage format](https://docs.adventure.kyori.net/minimessage.html#format) throughout the plugin, allowing the maximum possible customization.
- [MiniPlaceholders](https://modrinth.com/plugin/miniplaceholders) support

## Commands

To use the commands in Velocity, just add a "v" at the beginning of the command, for example: "/vannounce" or "/vtitleannouncer".

### Announce Command

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
            <td><code>/announce</code></td>
            <td>[title|bossbar|chat|actionbar] (Target Argument) (Format Arguments)</td>
            <td>announcer.command</td>
            <td>TitleAnnouncer Announce Command.</td>
        </tr>
    </tbody>
</table>

#### Target Argument



### Title Format

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
