# FOOP Final Report

## (1) the team members’ names and school IDs 
### 轉守為攻老炒麵寶
- B06303036 朱家慶
- B07902056 郭瑋喆
- B07902104 林治善
- B07902142 許庭維(Ryan Hsu)
---
## (2) how you divide the responsibilities of the team members
- 朱家慶
> 角色與怪物技能設計與操作辦法，擴充角色遊戲性。
- 郭瑋喆
> 遊戲座標計算(卷軸地圖)與GUI處理、遊戲流程翻新與登入介面帳號設計。
- 林治善
> 地圖地形設計與物件碰撞效果、模擬遊戲物理引擎。
- 許庭維 (Ryan Hsu)
> 整體遊戲藍圖設計與音訊處理、搜尋素材與前處理。（Overall game blueprint design and audio processing, material searching, and preprocessing.）
---
## (3) the relations between the classes that you design
- Classes in fsm will cope with the transition of state of starpixel and ironbear and knight.
- Game and game loop and gamecreator will together to run and switch between different worlds , including login page and game page.
- Knight and classes in model will construct and deal with the knight we control.
- Ironbear and starPixel and classes in model will construct and deal with the enemies we face.

---
## (4) the advantages of your design
1. Different from TA's code, we can change one world to another in simple way.
2. We support multiple account for new game.
3. We can easily add any skills, HP, MP, and behavior, etc. That is, we can bulid any character if we want to and do not conflict any rules. (Similar to OCP)
4. We will store any account's status. When we login next time, we can keep playing. (Autosave)
---
## (5) the disadvantages of your design
1. Some codes are subtlely messy, which are hard to maintain, such as jump and fall.
2. When multiple commands invoke in a sudden time, some of them may not work.
3. The determination of all object are not that much accurate because we set object's contour to rectangle instead of it's real contour.

---
## (6) other packages that you have used
1. packages that TA provide.
2. bufferedimage.
---
## (7) how to play your 2D game

### Compile and start the game
1. open terminal and cd in directory FOOP2021final.
```
cd ./FOOP2021final
```
2. use command below to compile the game.
```
javac -sourcepath src -d out/ src/*.java
```
3. use command below to start the game.
```
java -cp out/ Main
```
---
### Login
1. Enter account and password to verify user's identification.
2. Press $Enter$ to login the game.

#### User kuo
> Account: kuo2020 
> Password: 0000 

#### User weebao
> Account: weebaoweewee
> Password: 0000

---
### Play game
- Press $A, D$ for left, right.
- Press $E$ for basic attack.
- Press $U$ for special attack 1.
- Press $I$ for special attack 2.
- Press $Space$ for jump.
- Press $Space$ 2 times for double jump.
- Press $Esc$ for entering pause mode.
---
### Pause Mode
- Click $Restart$ to initialize game status.
- Click $Login$ to go back to login state.

## Reference
1. https://www.spriters-resource.com/pc_computer/maplestory/
2. https://taira-komori.jpn.org/freesoundtw.html
3. TA's code
