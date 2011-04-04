<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MobileCloudMiddleware task monitor</title>
        <link rel="stylesheet" type="text/css" href="styles/monitor.css" />
        <script type="text/javascript" src="scripts/jquery-1.5.2.js"></script>
        <script type="text/javascript" src="scripts/jquery.periodicalupdater.js"></script>
        <script type="text/javascript" src="scripts/monitor.js"></script>
    </head>
    <body>
        <h1>Task monitor</h1>
        <span>
            <p id="counter"></p>
        <form>
            <input type="button" id="recheck" value="Recheck"/>
        </form>
        </span>
        <table id="TaskStatuses">
            <thead>
                <th>Task</th>
                <th>Description</th>
                <th class="status">Status</th>
            </thead>
            <tbody>
                <tr id="task1">
                    <td>First task</td>
                    <td>Place holder for first task description</td>
                    <td class="status">Completed</td>
                </tr>
                <tr id="task2">
                    <td>Second task</td>
                    <td>I was started after the first one</td>
                    <td class="status">Running</td>
                </tr>
                <tr id="task3">
                    <td>Third task</td>
                    <td>Last one</td>
                    <td class="status">Waiting</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
