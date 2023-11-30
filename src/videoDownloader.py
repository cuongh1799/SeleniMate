from pytube import YouTube

link = input(("Enter youtube link: "))

video = YouTube(link)

print("You are about to download " + video.title + ", do you wish to continue ? ");
ans = input()

if ans == 'y':
    video.streams.filter(only_audio=True)
    stream = video.streams.get_by_itag(22)
    stream.download()

