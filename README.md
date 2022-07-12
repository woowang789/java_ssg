# java_ssg
- 자바 프로그램으로 명언 데이터 관리
- Git page를 이용한 자동 배포

## <a href="https://codepen.io/woowang789/pen/vYRKodz">코드펜에서 확인하기</a>

```javascript

// 데이터 불러오기
const loadWiseSayings = async () => {
    const data = await fetch("https://woowang789.github.io/java_ssg/data.json");
    const dataJson = await data.json();

    setWiseSayings(dataJson);

    const randomIndex = Math.floor(Math.random() * dataJson.length);
    setWiseSayingsIndex(randomIndex);
  };
```
(codepen written by <a href='https://www.youtube.com/user/jangka512/featured'>몰입코딩 아카이브</a>)

## 동작
<img width=749px src='https://user-images.githubusercontent.com/103170057/178419636-15a234e7-1284-482e-b23e-069232056768.png'>
<img width=749px src='https://user-images.githubusercontent.com/103170057/178419651-a41bad58-a258-430e-a791-75c016dc7db2.png'>
