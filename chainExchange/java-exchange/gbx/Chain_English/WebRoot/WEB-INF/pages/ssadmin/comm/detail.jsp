<%@ page pageEncoding="UTF-8"%>
<div class="accordion" fillSpace="sidebar">
	<shiro:hasPermission name="user">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>Member MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/userList.html">
					<li><a href="ssadmin/userList.html" target="navTab"
						rel="userList">Member's List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/userAuditList.html">
					<li><a href="ssadmin/userAuditList.html" target="navTab"
						rel="userAuditList">Pending Approval List (ID No.)</a></li>
					<li><a href="ssadmin/userAuditList1.html" target="navTab"
						rel="userAuditList1">Pending Approval List (Photocopy)</a></li>
					<li><a href="ssadmin/userAuditList2.html" target="navTab"
						rel="userAuditList2">Pending Approval List (Video ID)</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/logList.html">
					<li><a href="ssadmin/logList.html" target="navTab"
						rel="logList">Member's Operation Log</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/introlinfoList.html">
					<li><a href="ssadmin/introlinfoList.html" target="navTab"
						rel="introlinfoList">Promotion Earnings</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/bankinfoWithdrawList.html">
					<li><a href="ssadmin/bankinfoWithdrawList.html"
						target="navTab" rel="bankinfoWithdrawList">Member's Bank Account</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/virtualaddressWithdrawList.html">
					<li><a href="ssadmin/virtualaddressWithdrawList.html"
						target="navTab" rel="virtualaddressWithdrawList">Cryptocurrency Withdrawal Address For Member</a>
					</li>
					<li><a href="ssadmin/virtualaddressList.html" target="navTab"
						rel="virtualaddressList">Cryptocurrency Deposit Address For Member</a>
					</li>
				</shiro:hasPermission>
				<!-- <shiro:hasPermission name="ssadmin/assetList.html">
					<li><a href="ssadmin/assetList.html" target="navTab"
						rel="assetList">会员资产记录列表</a></li>
				</shiro:hasPermission> -->
				<shiro:hasPermission name="ssadmin/subscriptionList1.html">
					<li><a href="ssadmin/subscriptionList1.html" target="navTab"
						rel="subscriptionList1">ICO</a></li>
			<!-- 		<li><a href="ssadmin/subscriptionList.html" target="navTab"
						rel="subscriptionList">兑换列表</a>
					</li> -->
				</shiro:hasPermission>
				<!-- <shiro:hasPermission name="ssadmin/sharePlanList.html">
					<li><a href="ssadmin/sharePlanList.html" target="navTab"
						rel="sharePlanList">分钱计划列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/handingSharePlanList.html">
					<li><a href="ssadmin/handingSharePlanList.html"
						target="navTab" rel="handingSharePlanList">分币计划列表</a>
					</li>
				</shiro:hasPermission> -->
				<shiro:hasPermission name="ssadmin/entrustList.html">
					<li><a href="ssadmin/entrustList.html" target="navTab"
						rel="entrustList">Commissioned Transaction</a>
					</li>
					<li><a href="ssadmin/tradehistoryList.html" target="navTab"
						rel="tradehistoryList">Historical Closing Price</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="article">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>News MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/articleList.html">
					<li><a href="ssadmin/articleList.html" target="navTab"
						rel="articleList">News List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/articleTypeList.html">
					<li><a href="ssadmin/articleTypeList.html" target="navTab"
						rel="articleTypeList">News Type</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="capital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>Cryptocurrency Oper MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/virtualCoinTypeList.html">
					<li><a href="ssadmin/virtualCoinTypeList.html" target="navTab"
						rel="virtualCoinTypeList">List of Cryptocurrency Type</a>
					</li>
					<li><a href="ssadmin/tradeMappingList.html" target="navTab"
						rel="tradeMappingList">Trading Mapping List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/walletAddressList.html">
					<li><a href="ssadmin/walletAddressList.html" target="navTab"
						rel="walletAddressList">Available Cryptocurrency Address List</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCaptualoperationList.html">
					<li><a href="ssadmin/virtualCaptualoperationList.html"
						target="navTab" rel="virtualCaptualoperationList">General Table</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalInList.html">
					<li><a href="ssadmin/virtualCapitalInList.html"
						target="navTab" rel="virtualCapitalInList">Deposit List</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalOutList.html">
					<li><a href="ssadmin/virtualCapitalOutList.html"
						target="navTab" rel="virtualCapitalOutList">Pending Withdrawal List</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalOutSucList.html">
					<li><a href="ssadmin/virtualCapitalOutSucList.html"
						target="navTab" rel="virtualCapitalOutSucList">Completed Withdrawal List</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualwalletList.html">
					<li><a href="ssadmin/virtualwalletList.html" target="navTab"
						rel="virtualwalletList">Member's Cryptocurrency List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/virtualoperationlogList.html">
					<li><a href="ssadmin/virtualoperationlogList.html"
						target="navTab" rel="virtualoperationlogList">Manual Deposit List</a>
					</li>
				</shiro:hasPermission>
		<!-- 		<shiro:hasPermission name="ssadmin/transportlogList.html">
					<li><a href="ssadmin/transportlogList.html" target="navTab"
						rel="transportlogList">会员转帐记录列表</a></li>
				</shiro:hasPermission> -->
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="cnycapital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>USD Oper MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/capitaloperationList.html">
					<li><a href="ssadmin/capitaloperationList.html"
						target="navTab" rel="capitaloperationList">General Table</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalInSucList.html">
					<li><a href="ssadmin/capitalInSucList.html" target="navTab"
						rel="capitalInSucList">Deposit List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutSucList.html">
					<li><a href="ssadmin/capitalOutSucList.html" target="navTab"
						rel="capitalOutSucList">Withdrawal List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalInList.html">
					<li><a href="ssadmin/capitalInList.html" target="navTab"
						rel="capitalInList">Pending Deposit List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutList1.html">
					<li><a href="ssadmin/capitalOutList1.html" target="navTab"
						rel="capitalOutList1">Pending Withdrawal (1st Review)</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutList2.html">
					<li><a href="ssadmin/capitalOutList2.html" target="navTab"
						rel="capitalOutList2">Pending Withdrawal (2nd Review)</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutList3.html">	
					<li><a href="ssadmin/capitalOutList3.html" target="navTab"
						rel="capitalOutList3">Pending Withdrawal (Final Review)</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/walletList.html">
					<li><a href="ssadmin/walletList.html" target="navTab"
						rel="walletList">Member's USD List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/operationLogList.html">
					<li><a href="ssadmin/operationLogList.html" target="navTab"
						rel="operationLogList">Manual Deposit List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/withdrawFeesList.html">
					<li><a href="ssadmin/withdrawFeesList.html" target="navTab"
						rel="withdrawFeesList">USD Withdrawal Fee List</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="report">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>Report Statistics
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/userReport.html">
					<li><a href="ssadmin/userReport.html" target="navTab"
						rel="userReport">Member Registration Statistics</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitaloperationReport.html">
					<li><a
						href="ssadmin/capitaloperationReport.html?type=1&status=3&url=ssadmin/capitaloperationReport"
						target="navTab" rel="capitaloperationReport">USD Deposit Statistics</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitaloperationOutReport.html">
					<li><a
						href="ssadmin/capitaloperationReport.html?type=2&status=3&url=ssadmin/capitaloperationOutReport"
						target="navTab" rel="capitaloperationOutReport">USD Withdrawal Statistics</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/vcOperationInReport.html">
					<li><a
						href="ssadmin/vcOperationReport.html?type=1&status=3&url=ssadmin/vcOperationInReport"
						target="navTab" rel="vcOperationInReport">Cryptocurrency Deposit Statistics</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/vcOperationOutReport.html">
					<li><a
						href="ssadmin/vcOperationReport.html?type=2&status=3&url=ssadmin/vcOperationOutReport"
						target="navTab" rel="vcOperationOutReport">Cryptocurrency Withdrawal Statistics</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/totalReport.html">
					<li><a href="ssadmin/totalReport.html" target="navTab"
						rel="totalReport">Comprehensive Statistics</a>
					</li>
					<li><a href="ssadmin/userWalletReport.html" target="navTab"
						rel="userWalletReport">Earning Statistics</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="question">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>Question MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/questionList.html">
					<li><a href="ssadmin/questionList.html" target="navTab"
						rel="questionList">Question  Records List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/questionForAnswerList.html">
					<li><a href="ssadmin/questionForAnswerList.html"
						target="navTab" rel="questionList">Pending Questions List</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="vote">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>New Coin Vote MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="/ssadmin/coinVoteList.html">
					<li><a href="/ssadmin/coinVoteList.html" target="navTab"
						rel="coinVoteList">Voting Info List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="/ssadmin/coinVoteLogList.html">
					<li><a href="/ssadmin/coinVoteLogList.html" target="navTab"
						rel="coinVoteLogList">Voting Record List</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="system">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>System MENU
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/systemArgsList.html">
					<li><a href="ssadmin/systemArgsList.html" target="navTab"
						rel="systemArgsList">System Parameters</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/linkList.html">
					<li><a href="ssadmin/linkList.html" target="navTab"
						rel="linkList">Link List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/systemBankList.html">
					<li><a href="ssadmin/systemBankList.html" target="navTab"
						rel="systemBankList">Bank Account List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/aboutList.html">
					<li><a href="ssadmin/aboutList.html" target="navTab"
						rel="aboutList">Help Categorie List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/securityList.html">
					<li><a
						href="ssadmin/goSecurityJSP.html?url=ssadmin/securityTreeList&treeId=1"
						target="navTab" rel="securityTreeList">Permission List</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/roleList.html">
					<li><a href="ssadmin/roleList.html" target="navTab"
						rel="roleList"> Role List</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/adminList.html">
					<li><a href="ssadmin/adminList.html" target="navTab"
						rel="adminList">Admin List</a>
					</li>
					<li><a href="ssadmin/countLimitList.html" target="navTab"
						rel="countLimitList">Constraint List</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/limittradeList.html">
					<li><a href="ssadmin/limittradeList.html" target="navTab"
						rel="limittradeList">Price Limit Trading List</a>
					</li>
					<li><a href="ssadmin/autotradeList.html" target="navTab"
						rel="autotradeList">Automated Trading List</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

</div>