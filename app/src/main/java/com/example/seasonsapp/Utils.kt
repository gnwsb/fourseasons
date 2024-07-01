package com.example.seasonsapp

data class SongInfo(val title: String, val artist: String, val link: String)

val songInfos = mapOf(
    R.raw.spr1 to SongInfo("이소라 7집", "이소라", "https://open.spotify.com/album/25evl4uFTZnYnLcUwrl0Oa?si=Jvfwzi0cQBK2sbx8BhF03g"),
    R.raw.spr2 to SongInfo("Ants from Up There", "Black Country, New Road", "https://open.spotify.com/album/21xp7NdU1ajmO1CX0w2Egd?si=9e2f629f83294376"),
    R.raw.spr3 to SongInfo("ハチミツ", "Spitz", "https://open.spotify.com/album/4mz1WjnsW88FxFfvtrfIAd?si=FSdQji_TSbWzHSU_rkffCg"),
    R.raw.spr4 to SongInfo("Javelin", "Sufjan Stevens", "https://open.spotify.com/album/2KqSL3vLfyVO7rrZJL9tUs?si=314f4a0717fe4613"),
    R.raw.spr5 to SongInfo("ロンググッドバイ", "Kinoko Teikoku", "https://open.spotify.com/album/2qqwUNewGqmBiV7jUdqqQd?si=81ZwTWtQQxmSrjYmGOSnxQ"),
    R.raw.spr6 to SongInfo("Abbey Road", "The Beatles", "https://open.spotify.com/album/0ETFjACtuP2ADo6LFhL6HN?si=e9d2d7c14e214adb"),
    R.raw.sum1 to SongInfo("For You", "Tatsuro Yamashita", "https://music.youtube.com/watch?v=BZ9Ib2zSqSk&si=APARDK6jD9uZ-cbj"),
    R.raw.sum2 to SongInfo("Masked Dancers: ...", "Brave Little Abacus", "https://open.spotify.com/album/5UiQKq5B5Y3J4Qepcx2ioK?si=82b0e3b2cb2245ac"),
    R.raw.sum3 to SongInfo("To See the Next Part...", "파란노을", "https://open.spotify.com/album/5IyHtkKQvafw7bQYFnx4FO?si=f4bcbc03758849eb"),
    R.raw.sum4 to SongInfo("To Pimp A Butterfly", "Kendrick Lamar", "https://open.spotify.com/album/7ycBtnsMtyVbbwTfJwRjSP?si=9f8fdbbee6004430"),
    R.raw.sum5 to SongInfo("서울병", "쏜애플", "https://open.spotify.com/album/0glXbKYknQlnd2FnxsxWez?si=00ae4b503f4b42dc"),
    R.raw.sum6 to SongInfo("スウィートソウル EP", "Kirinji", "https://open.spotify.com/album/4oYbhdzJaeH0g7i95DMcjp?si=FGMtxZ-mRNa41jCO7dc8Zg"),
    R.raw.aut1 to SongInfo("Kind of Blue", "Miles Davis", "https://open.spotify.com/album/1weenld61qoidwYuZ1GESA?si=792f2dc6a23c43ba"),
    R.raw.aut2 to SongInfo("Blonde", "Frank Ocean", "https://open.spotify.com/album/3mH6qwIy9crq0I9YQbOuDf?si=582a4f9375604b30"),
    R.raw.aut3 to SongInfo("Loveless", "My Bloody Valentine", "https://open.spotify.com/album/3USQKOw0se5pBNEndu82Rb?si=58ff832b4b134766"),
    R.raw.aut4 to SongInfo("Metaphorical Music", "Nujabes", "https://open.spotify.com/album/5FrjDW96mCYw9ECc74c637?si=d16dda93020243de"),
    R.raw.aut5 to SongInfo("卵", "betcover!!", "https://open.spotify.com/album/4qe0XanRyN4rIkJxjdSoh6?si=Ez3FtgcmRsSvrWXdD-G6wA"),
    R.raw.aut6 to SongInfo("Scenery", "Ryo Fukui", "https://open.spotify.com/album/5Uny0mkKiVGDat7H6SNDyS?si=oVhPkeehRQ-CwbtroF-EiQ"),
    R.raw.win1 to SongInfo("무너지기", "공중도둑", "https://open.spotify.com/album/4iLo2HykYzbHw6MWSTg4vp?si=a8998138abfa4c1a"),
    R.raw.win2 to SongInfo("Wish You Were Here", "Pink Floyd", "https://open.spotify.com/album/0bCAjiUamIFqKJsekOYuRw?si=MWR77da5QdWtxEEZdhnxyw"),
    R.raw.win3 to SongInfo("You Must Believe In...", "Bill Evans", "https://open.spotify.com/album/2B583jxnkHmIyBU6Z8VlmI?si=SB4gKuTpRPeXPiu8r7tCCA"),
    R.raw.win4 to SongInfo("가장 보통의 존재", "언니네 이발관", "https://open.spotify.com/album/4QEgFm1iVCWyHiSTM72YGX?si=cc2b3c67d8824c98"),
    R.raw.win5 to SongInfo("加爾基 精液 栗ノ花", "Sheena Ringo", "https://open.spotify.com/album/2i4fy6a51XTaZOYA8rIdRK?si=843e2a3a14ad4471"),
    R.raw.win6 to SongInfo("Madvillainy", "Madvillain", "https://open.spotify.com/album/19bQiwEKhXUBJWY6oV3KZk?si=62d71b5f9513422c")
)