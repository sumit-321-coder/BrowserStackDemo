# BrowserStackDemo
Build With:
Selenium 4.27, Java 1.8, TestNG, Maven , rest assured (for tranlation from spanish to english)

Cloud execution on:
BrowserStack

Functionality:
This code:
> Launches the Browser,
> Navigate to spanish site (https://elpais.com/),
> Navigate to Opinion section,
> Selects first five article,
> Navigate to each article,
> Print each article heading in Spanish,
> Print each article content in Spanish,
> Downloads the artice image and save in Project images folder,
> Convert and Print article heading from spanish to English using rest assured,
> Combines all five articles english heading and prints the word which has occured more than twice.

To run the code:
Execute the testng.xml file as testNG-suite

Default Setting:
Parallel execution across 5 treads in browserstack cloud platform

To change the number of threads:
Modify the thread count in testNG.xml file
