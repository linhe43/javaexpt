package leetcode;

import util.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class WordSquares {

    class TrieNode {
        char val;
        TrieNode[] children;
        List<String> words;

        TrieNode(char v) {
            val = v;
            children = new TrieNode[26];
            words = new ArrayList<>();
        }
    }

    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> ret = new ArrayList<>();
        if (words == null || words.length == 0) return ret;

        // build the tree using TrieNode;
        TrieNode root = new TrieNode(' ');
        for (String word : words) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char val = word.charAt(i);
                if (cur.children[val - 'a'] == null) {
                    cur.children[val - 'a'] = new TrieNode(val);
                }
                cur = cur.children[val - 'a'];
                cur.words.add(word);
            }
        }

        // traverse the tree to build word squares;
        int wordLen = words[0].length();
        for (String word : words) {
            List<String> curSqr = new ArrayList<>();
            curSqr.add(word);
            traverse(root, curSqr, wordLen, ret);
        }

        return ret;
    }

    private void traverse(TrieNode root, List<String> curSqr, int wordLen, List<List<String>> ret) {
        int curSize = curSqr.size();
        if (curSize == wordLen) {
            ret.add(new ArrayList<>(curSqr));
            return;
        }

        TrieNode cur = root;
        for (int i = 0; i < curSize; i++) {
            char val = curSqr.get(i).charAt(curSize);
            if (cur.children[val - 'a'] == null) return;
            cur = cur.children[val - 'a'];
        }

        // try all the words recorded in current TrieNode;
        for (String word : cur.words) {
            curSqr.add(word);
            traverse(root, curSqr, wordLen, ret);
            curSqr.remove(curSqr.size() - 1);
        }
    }

    public static void main(String[] args) {
        String[] words = new String[] {"ldqv","tibp","koey","dsdh","wxjz","glam","eyyy","ymeo","yjjp","qqjq","qsra","eldf","fcgc","fuqs","awgs","wcjp","pafp","amlz","uzql","rtre","sxur","frvx","lvwn","zbfv","ekfe","ugac","mqel","ryzg","uxfb","urea","vdir","xxeg","ipuq","vuxx","nzou","bsid","aows","schd","bkto","jrpm","cctl","koiu","vzaf","viuc","gnwm","sdvg","gvyu","bqkl","mtvj","wwpp","cyhe","hqpi","enoq","puhc","aknu","vwbg","bafk","bnhg","gcny","xdap","zmgr","pdpj","kpef","trms","miwe","bakx","vpbr","naiw","xlzj","bocb","tyyk","osqw","hhia","scer","igjz","tvsy","oron","tlqz","leyz","mgwb","ebbo","vmwm","nuxb","gunb","tjuj","oezm","spro","bjzo","jnjx","ucbu","yfpw","fmhl","xkfp","bnij","ihwn","fvci","isxg","svim","msyg","sjfs","rczg","vioc","ywrg","ebkr","noiu","hkhc","udtr","kxdf","qxgk","jziu","hjwb","oulh","kidq","mzks","rekt","pnye","bhup","vwwv","bxop","hyvv","aoae","ephf","fixl","jpjq","wzmb","ygzw","hyva","cjgu","ojxa","ovaw","jznc","duct","aotz","ryor","rchy","wktq","mwtt","ougt","lkks","zraz","jghv","oecr","icej","szfa","cilr","rhej","rgwm","mzws","lymr","htch","abva","vfhw","lgbz","igud","warz","grti","xycf","ffel","kqqs","pmyx","hxub","vdma","tdph","fxfw","drpf","yial","vgwr","uary","rdgu","kyoj","ygfg","yvet","muzi","vydu","sabk","cylc","eiys","ozfz","sdrq","xwnf","laqb","apfd","tqci","gpvm","qxbn","ednm","qara","iawb","lzvs","spvv","hdbq","mrgu","mkfy","hxdt","qczg","nxwy","uzlm","jfde","nwao","satz","ruoz","sruw","iwnk","dclt","smss","lhto","hihh","zrsq","xjfe","jxkf","wgpb","ptfl","hnjz","yxjq","yqyk","xeib","mjpo","blhi","xksx","smju","xazs","zujb","xrmt","nrgs","zimw","dove","rzjk","rhbl","doaz","pdnx","tktr","fgzd","jdcs","yuqv","tlch","mdak","fybt","ewzh","inza","qakq","zkma","rrga","falm","ngxs","xbda","xbdg","nsfm","uqvi","exft","eozp","fabz","azbc","wmpb","ctpn","udhn","yvxk","pqxr","zcde","zbsh","vgzv","qdot","ozeu","jcdn","uvri","maib","kxml","nytx","vwac","pzhx","poqa","vjeq","grph","skqt","eyak","yqle","yhpe","urmq","wmnj","eupp","juav","lzab","vpga","jmho","icpv","hgak","oqzp","jhce","trkw","foog","bnvj","teri","sevi","pgaf","hugy","llpn","xrcz","fjya","ydjh","ckzr","xhcn","eeyw","ckzx","ietb","gtah","wnut","knzc","ahvp","aqbh","dxmf","eeyc","wzwi","uakc","yeap","exyh","kanw","ygum","ytfn","hhak","wbrl","bvcb","ogzh","ufax","cvxp","jpkc","bhff","mgws","ybiz","daph","abhn","bvjf","xtma","ukuw","dapu","qigj","blmj","loic","mnaw","qlyv","ycsz","fkua","dhzg","ctwf","ejui","ayrt","wxiy","zsng","vjpq","gvjc","epyg","xnmk","rwaa","gjzb","jhqd","yurf","lwek","xnme","xyur","ufsd","bmhc","wwwc","atjg","voos","ofjq","owhc","oklh","dejn","lzdb","szla","mrxq","hssr","oicv","cads","oafg","uvvk","yonk","xohx","voic","wekh","yygg","odtz","criz","qcps","vxfg","thjz","gbgx","gkcq","bgjz","yxfy","yggl","lclm","rqbb","kftb","wekb","xzir","kmqv","fpwy","kipl","fvgt","kmqh","ovnm","rfiq","vhjo","hvcg","wpwf","rgvt","tkyl","zyyz","exkq","dynw","uvug","unqa","rjqm","nfsi","rogj","fqvr","zxtj","eamr","oxap","tmoh","qels","ntic","zmsu","htzi","lxbe","cemy","sxae","qppg","vndx","tbbc","jtjn","zezb","fctj","irud","vgkh","zsad","aeqn","pxsa","mywd","lktx","lyzn","uhqh","qheo","qylj","twxv","kffg","wrio","nebh","tsga","omfr","kkep","qgqe","bppz","ojrx","ilqs","fgcb","sayj","spga","qbtt","jnzf","uuxa","bsfw","djwd","jygn","tzwv","dmco","hofl","lrqy","thty","xibo","mgek","aexq","wgxs","eega","swcp","rvxo","essd","opxr","foph","yqqb","uqxh","tmtn","syac","rvxj","ycex","xwpi","lbih","jqwg","cfmy","erbk","ycku","fiej","oghu","erbo","uyug","nmif","denc","toik","owdd","hbxf","fhkh","jksd","dnbn","ujem","rlwc","oojq","vzqc","vsxh","wrzv","xmlu","qeiw","vebr","jgrz","bgdg","bjqc","xnuk","kwti","aiwc","evnv","gttd","ntma","ffdo","ublq","fjzw","cgya","jukv","hwvx","rblz","uyvg","gkil","ukoe","ainn","lekg","jwcc","xndl","tnvd","sskz","ibka","hkvn","jdno","yvir","kwvu","npzu","zwpe","mguj","gxsl","awfx","rlbe","dlxw","ehvp","gpuu","leud","dqet","tqkg","pwwe","lyqz","hcay","graj","jaqb","raxj","snfq","rpij","vffm","fnlo","ymki","etik","sipm","lkoi","tcnq","oxpd","kvac","yaxe","xmvv","izxo","foss","yzgd","dgub","gnhj","nqpg","htai","zbny","rlld","kmom","uyoy","joyw","mvcd","fcmm","gagc","qrdf","vprp","gkur","joyd","rvyz","ywip","tihz","udbx","hfhs","jxdo","vhtq","jmri","snpv","fvmi","yumq","mqhb","rccb","ixud","zhxb","bzoz","pkpb","opag","axzf","nlkk","ilmm","xqyu","xgvz","zxim","sjvz","wjsj","khew","oxjb","giri","tavh","xffa","aasl","zngx","ygoi","mvor","zdwq","yhwn","vxys","jbud","jxgu","kpkz","tmnk","xjxg","nqbg","zfwt","zpee","coqp","iyrz","zklv","dgvg","fbqo","vpkz","aijr","yeog","iyru","xemr","qqft","jtkj","omwr","vfbz","yizn","qqfs","dcip","whog","noeg","gwii","wkje","hhbz","exrl","cmyx","bulm","gjqy","uahk","davi","okjn","jhvc","gwwp","rvdu","eeqd","rsje","vlco","lhqj","bjdq","hnou","pqdf","jzbv","iobg","eyqb","hoam","rzzy","ctrc","hoab","yidi","ypup","mpqj","cjrf","kzib","xhvp","gimw","zsig","nlpm","mdxk","jftn","fkpj","eajd","pxbh","lyks","zopy","apcl","kxoo","ecpu","uzuc","jouj","kxog","cfdn","aktr","udfu","lgvc","oiny","uwci","fefg","oago","btdy","ofvg","vzla","fedn","cpts","ewfy","thkl","dfwm","xxgb","zqle","ungi","ngmr","ooip","fxdp","eviy","shjs","cuqu","ygan","qwvi","pwru","xnyc","wpvw","ojhz","okqg","nolf","kwht","osdv","kfwp","mmvr","skzx","mwda","dghb","bvvh","qlcu","adbc","hesk","rypw","dezb","jjqd","irbf","wqqi","tlwz","nwfx","ntuq","wqqu","zkvu","hdlw","hzfx","czvw","uqli","alum","zqgp","cbbk","lfeh","wagb","vrpl","snny","gfzg","chps","edyc","mzle","mpcg","qous","upyn","natl","ftco","ukmc","kbtf","upye","fgbf","frcm","ytdu","srlb","ycqu","pfbs","gamy","ditz","bceh","nedl","bmpc","xxab","uquk","gmvi","gamn","qtgn","imln","bvox","uela","xzza","ydsw","fqbu","zgoi","pfcu","pdil","kuln","aeyy","oade","wlco","euwh","dhsq","htii","blys","jtzg","yrvb","lcef","qrlq","dzcz","kbxs","urbt","xgqq","xasg","ucsu","hhqa","txzd","ozgk","mook","rohf","hojd","fema","gsfj","edby","lvdg","czxq","bbyl","yiwb","rkie","vedk","pueg","yksc","lvdm","ghsi","lswv","ttjt","rdaf","uezp","ndbp","lsbr","phel","anwe","mjcz","ngfs","mkei","tixh","oyvx","lxyx","xftd","aeol","iwaj","nnlg","trgg","gefc","bgln","nmnr","cmal","rqic","nnlp","rqif","slkq","ylzq","mazo","wepn","hqnd","hkmx","onxu","zukm","yrcp","qerl","dowl","ehsu","efyv","fzpi","mfny","vtfv","hzbw","zlvt","gjmv","smbe","wwhz","qzrz","ugml","rowz","pylj","nsji","imij","cjat","sojk","lzcy","jzcq","rowk","bcsz","ecqy","witq","kjxi","eeih","ymha","mzon","yjtl","vwws","kcwe","rvrf","pmph","uzvk","pxho","uszb","csox","byor","ovge","zotp","mebc","iisf","xjkm","zarv","nkfx","flih","jxbc","wisy","zptw","gtqn","orxa","wnum","ttlg","qsgz","cafz","eusu","cqqh","dmun","tnhw","royc","tftk","yagc","sftr","usfr","wcid","teza","isdg","ckog","dysy","rjbi","ltlm","mlol","yzsg","ptkt","doyr","rbri","okva","skiu","iwfr","ebfv","tojg","uvmr","pzbe","wnij","iezr","sdcg","kpan","mfec","cmfx","bfen","ulai","exrm","jaxf","vfdr","nxvk","iodt","vcdd","epbo","tbie","mnuw","qjay","edop","ioav","ohkj","ucmh","vqss","oavy","eeak","egwg","sljt","xnam","ffab","puse","znoq","pmhf","bjrl","syxs"};

        String[] words1 = new String[] {
                "abat","baba","atan","atal"
        };

        long start = System.currentTimeMillis();
        List<List<String>> ret = new WordSquares().wordSquares(words);
        long end = System.currentTimeMillis();
        for (List<String> list : ret) {
            ListUtil.println(list);
        }
        System.out.format("Found %d squares%n", ret.size());
        System.out.format("Used time: %d ms%n", end - start);
    }
}

