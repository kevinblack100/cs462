<html>
	<head>
		<title>Sign In</title>
	</head>
	<body>
		<h1>Sign In</h1>
		<form method="POST" action="./authenticate">
			<fieldset>
				<label for="username">Username:</label>
				<input id="username" name="username" type="text" size="20" />
				<br />
				<label for="password">Password:</label>
				<input id="password" name="password" type="password" size="20" />
				<br />
				<input id="submit" name="submit" type="submit" value="Sign In" />
			</fieldset>
		</form>
	</body>
</html>