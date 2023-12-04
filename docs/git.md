> 本文档主要记录个人遇到的Git问题及解决方法或相关知识。

# 问题

### Your push would publish a private email address

提示“Your push would publish a private email address”的错误是因为在github或gitee的邮箱设置里勾选了“不公开我的邮箱地址”，而在本地的设置里又把自己的邮箱填成私人邮箱了。

首先从gitee或github的邮箱设置里找到为你随机生成的一个邮箱地址，然后把本地设置的邮箱更改为这个邮箱

```sh
# random_public_mail@email.com一定不能填成私有邮箱
git config --local user.name "username"
git config --local user.email random_public_mail@email.com
```

接下来是最重要的一步。由于之前的代码已经commit了，也就是私人邮箱已经进入log记录了，因此需要修改之前这次提交的信息，重做commit[^1]。否则再怎么push也会一直报“Your push would publish a private email address”的错误。

```git
git commit --amend --reset-author
```

最后，重新push即可。

### 修改多个commit记录的用户

[Your push would publish a private email address](# Your push would publish a private email address)这一小节讲的是修改最近一次commit的用户，如果需要修改的commit不止一个，那需要这样：

1. 首先`git log`查看提交记录，比如最近的记录是A，然后由近至远依次是A B C D E，其中A B D的用户需要修改。

2. 采用`git config`命令修改为正确的用户名，邮箱等等。

3. 从E开始，用`rebase`修改A B D的用户：

   ```sh
   git rebase -i E --exec="git commit --amend --no-edit --reset-author"
   ```

   如果所有记录包括第一次都要修改的话[^2][^3]：

   ```sh
   git rebase -i --root --exec="git commit --amend --no-edit --reset-author"
   ```

4. 接下来，如果所有修改的记录都只是本地的记录，没有push到远程仓库，那直接push即可。

   1. 如果部分包含错误用户的记录已经提交到远程，那需要rebase一下，把本地的记录rebase到远程上面，然后对于只更改了用户的记录会自动合并，这样远程看起来除了用户之外没有其他变化，也不会凭空多出一堆仅用户不同其他内容完全相同的记录。

      ```sh
      git checkout master
      # 这个命令在intellij IDE上面对应的按钮是rebase master onto origin/master。
      git rebase origin/master
      ```

### 操作一直提示输入密码

即使添加了SSH key，再执行git push/pull还是需要输入密码。原因是origin协议错了。

解决方法：

1. 查看origin：

   ```sh
   git remote get-url origin
   ```

   然后会发现是形如`https://github.com/Your_Name/Your_Repo.git`的形式，因为是https协议所以必须登录才能使用。

2. 修改origin协议：

   ```sh
   git remote set-url origin git@github.com:Your_Name/Your_Repo.git
   ```

   然后再次执行1中的命令就会发现已经改成git协议了[^4]。

3. 接下来再执行git push/pull就不需要密码了。

### reset后修改记录丢失

注意必须是commit过的记录才可以找回，没commit的只能重新写了。

```sh
# 查看所有变更记录，包括已经删除的提交
git reflog
# 找到commit过的记录对应的Hash，然后reset
git reset --hard Hash
```

方便起见最好修改一下reflog的过期时间为永不过期（默认是90天）[^5]：

```sh
git config gc.reflogexpire --global never
```

### Git修改最近一次push的记录的comment内容

1. 首先修改本地评论：

   ```sh
   git commit --amend -m "修改后的评论"
   ```

2. 然后强制push[^6]：

   ```sh
   git push --force-with-lease
   ```

### 合并最近几次提交的简单办法

reset head，commit然后强制push（但要注意分支，避免push错了）[^7]。

```sh
git reset --soft HEAD~2 
git commit -m "new commit message"
git push -f origin your_branch
```

# 参考资料

[^1]: [error: GH007: Your push would publish a private email address.](https://www.cnblogs.com/ramlife/p/16874362.html)

[^2]: [How to change the author of multiple Git commits ](https://www.ankursheel.com/blog/change-author-multiple-git-commits)

[^3]: [How do I change the author and committer name/email for multiple commits?](https://stackoverflow.com/questions/750172/how-do-i-change-the-author-and-committer-name-email-for-multiple-commits#comment46078300_1320317)注意看的是评论，不是评论上面的回答

[^4]: [git配置ssh登陆后，却一直提示要输入密码？](https://www.zhihu.com/question/55865892/answer/2139046618)

[^5]: [用Reflog恢复丢失的提交](https://zhuanlan.zhihu.com/p/639564741)

[^6]: [Changing git commit message after push (given that no one pulled from remote)](https://stackoverflow.com/a/8981216)

[^7]: [How do I squash my last N commits together?](https://stackoverflow.com/a/61171280)