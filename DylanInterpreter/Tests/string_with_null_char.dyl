function main() returns integer {
	string x = "hello\0 world!";
	print(x + "\n");
	print("hello2\0 world" + "\n");
	print("hello\0 world" || " person\0 dog" + "\n");
	return 0;
}