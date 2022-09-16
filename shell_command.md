# shell command

## [zgrep](https://www.geeksforgeeks.org/zgrep-command-in-linux-with-examples/?ref=gcse)
zgrep命令用于从给定的文件中搜索表达式，即使该文件已被压缩。适用于grep命令的所有选项也适用于zgrep命令

## [grep](https://www.geeksforgeeks.org/grep-command-in-unixlinux/?ref=gcse)
grep筛选器在文件中搜索特定的字符模式，并显示包含该模式的所有行。在文件中搜索的模式被称为正则表达式(grep代表正则表达式和打印的全局搜索)。

## [awk](https://www.geeksforgeeks.org/awk-command-unixlinux-examples/?ref=gcse)

Awk是一种用于操作数据和生成报告的脚本语言。awk命令编程语言不需要编译，并且允许用户使用变量、数值函数、字符串函数和逻辑运算符。

Awk是一种实用工具，它使程序员能够以语句的形式编写小型但有效的程序，这些语句定义了在文档的每一行中搜索的文本模式，以及在一行中找到匹配时要采取的操作。Awk主要用于模式扫描和处理。它搜索一个或多个文件，查看它们是否包含与指定模式匹配的行，然后执行相关操作。

Awk是开发人员姓名的缩写——Aho、Weinberger和Kernighan。

## [tcpdump](https://www.geeksforgeeks.org/tcpdump-command-in-linux-with-examples/?ref=gcse)

tcpdump是一个包嗅探和包分析工具，用于系统管理员解决Linux中的连通性问题。它用于捕获、过滤和分析网络流量，例如经过系统的TCP/IP包。它也经常被用作安全工具。它将捕获的信息保存在一个pcap文件中，这些pcap文件可以通过Wireshark或通过命令工具本身打开。
可结合 [wireshark](https://www.wireshark.org) 做分析

## telnet

## [xargs](https://www.geeksforgeeks.org/xargs-command-unix/?ref=gcse)
xargs是一个Unix命令，可用于从标准输入构建和执行命令。
重要性:
像grep这样的一些命令可以接受作为参数的输入，但是有些命令接受参数，这就是xargs出现的地方。

## [sed](https://www.geeksforgeeks.org/sed-command-in-linux-unix-with-examples/?ref=gcse)
SED命令在UNIX中代表流编辑器，它可以对文件执行许多功能，如搜索、查找和替换、插入或删除。尽管在UNIX中SED命令最常见的用途是替换或查找和替换。通过使用SED，您甚至可以在不打开文件的情况下编辑文件，这是查找和替换文件中某些内容的更快的方法，而不是先在VI编辑器中打开该文件然后更改它。

## [wc](https://www.geeksforgeeks.org/wc-command-linux-examples/?ref=gcse)
wc 代表 word count。顾名思义，它主要用于计数。

## [paste](https://www.geeksforgeeks.org/concatenate-two-strings-in-r-programming-paste-method/?ref=gcse)
在R编程中使用paste()方法通过用分隔符分隔来连接两个字符串值

## [expr](https://www.geeksforgeeks.org/expr-command-in-linux-with-examples/?ref=gcse)
Unix中的expr命令计算给定的表达式并显示其相应的输出。它用于:
基本运算，如整数的加、减、乘、除和模。
计算正则表达式，字符串操作，如子字符串，字符串长度等。

## [jq](https://stedolan.github.io/jq)
jq是一个处理JSON输入的工具，将给定的过滤器应用到
它的JSON文本输入和产生过滤器的结果作为JSON
标准输出。
最简单的过滤器是.，它将jq的输入复制到它的输出
未修改(格式除外，但注意使用了IEEE754
对于内部的数字表示，以及它所暗示的一切)

## [tr](https://www.geeksforgeeks.org/tr-command-in-unix-linux-with-examples/?ref=gcse)
UNIX中的tr命令是用于转换或删除字符的命令行实用程序。它支持一系列转换，包括大写到小写、压缩重复字符、删除特定字符和基本的查找和替换。它可以与UNIX管道一起使用，以支持更复杂的转换。Tr代表翻译。

## [netstat](https://www.geeksforgeeks.org/netstat-command-linux/?ref=gcse)
Netstat命令显示各种网络相关信息，如网络连接、路由表、接口统计信息、伪装连接、组播成员等

## [ssh](https://www.geeksforgeeks.org/ssh-command-in-linux-with-examples/?ref=gcse)
ssh代表“Secure Shell”。它是一种用于安全连接到远程服务器/系统的协议。SSH是安全的，因为它在主机和客户机之间以加密的形式传输数据。它将输入从客户机传输到主机，并将输出传回。ssh在TCP/IP端口22上运行。

## [scp](https://www.geeksforgeeks.org/scp-command-in-linux-with-examples/?ref=gcse)
scp (secure copy)命令在Linux系统中用于在服务器之间安全拷贝文件。SCP命令或安全复制允许在本地主机和远程主机之间或在两个远程主机之间安全地传输文件。它使用与在Secure Shell (SSH)协议中使用的相同的身份验证和安全性。SCP以其简单性、安全性和预安装可用性而闻名。

## [Networking Tools](https://www.geeksforgeeks.org/linux-networking-tools/?ref=gcse)
可以使用各种网络工具来执行任务，例如获取关于网络上其他系统的信息、访问其他系统以及直接与其他用户通信。可以使用**ping**, **finger**, **traceroute**, **host**, **dig**, **nslookup etc**等工具获取网络信息。这对于较小的网络非常有用，并且能够直接访问远程系统来复制文件或执行命令