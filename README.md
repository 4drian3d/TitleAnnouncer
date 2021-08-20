# TitleAnnouncer ![WorkFlow Status](https://img.shields.io/github/workflow/status/4drian3d/TitleAnnouncer/TitleAnnouncer%20Maven%20Build?&style=flat-square) ![Version](https://img.shields.io/github/v/release/4drian3d/TitleAnnouncer?color=FFF0&style=flat-square)

A lightweight plugin to send Titles and Actionbars with the MiniMessage format.


## Commands

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
            <td><code>/testtitle</code></td>
            <td>(Title); (SubTitle)</td>
            <td>announcer.title.test</td>
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
            <td><code>/testactionbar</code></td>
            <td>(Message)</td>
            <td>announcer.actionbar.test</td>
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


## Compatibility
The plugin works in Paper, Tuinity, Airplane and Purpur 1.16.5 - 1.17.1

It does not and will not work in Spigot or CraftBukkit. It will not work in Paper or fork versions 1.16.4 or lower.
