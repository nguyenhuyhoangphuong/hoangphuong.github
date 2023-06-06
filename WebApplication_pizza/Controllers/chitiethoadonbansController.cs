using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class chitiethoadonbansController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/chitiethoadonbans
        public IQueryable<chitiethoadonban> Getchitiethoadonbans()
        {
            return db.chitiethoadonbans;
        }

        // GET: api/chitiethoadonbans/5
        [ResponseType(typeof(chitiethoadonban))]
        public IHttpActionResult Getchitiethoadonban(string id)
        {
            chitiethoadonban chitiethoadonban = db.chitiethoadonbans.Find(id);
            if (chitiethoadonban == null)
            {
                return NotFound();
            }

            return Ok(chitiethoadonban);
        }
        // get chi tiet hoa din 
        [HttpGet]
        [Route("chitiethdb/{tentg}")]
        public List<chitiethoadonban> getbytentg2(string tentg)
        {
            List<chitiethoadonban> dsin = db.chitiethoadonbans.ToList();
            List<chitiethoadonban> dsout = new List<chitiethoadonban>();
            foreach (chitiethoadonban ls in dsin)
            {
                String a = "" + ls.mahdb;
                if (a.Contains(tentg))
                    dsout.Add(ls);
            }
            return dsout;
        }
        //get tong gia 
        [HttpGet]
        [Route("thongkemasp/{tentg}")]
        public IHttpActionResult getMasp(string tentg)
        {
            int tong = 0;
            int soluong = 0;
            List<chitiethoadonban> dsin = db.chitiethoadonbans.ToList();
            foreach (chitiethoadonban ls in dsin)
            {
                if (ls.mahdb.Contains(tentg))
                {
                    String[] a = ls.dongia.Split('k');
                    tong = tong + int.Parse(a[0]) * int.Parse(ls.soluong);
                    soluong = soluong + int.Parse(ls.soluong);
                }
            }
            return Ok("Số lượng mua là: "+soluong+", Tổng doang thu là: "+tong+"K");
        }
        // PUT: api/chitiethoadonbans/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Putchitiethoadonban(string id, chitiethoadonban chitiethoadonban)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != chitiethoadonban.mahdb)
            {
                return BadRequest();
            }

            db.Entry(chitiethoadonban).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!chitiethoadonbanExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/chitiethoadonbans
        [ResponseType(typeof(chitiethoadonban))]
        public IHttpActionResult Postchitiethoadonban(chitiethoadonban chitiethoadonban)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.chitiethoadonbans.Add(chitiethoadonban);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (chitiethoadonbanExists(chitiethoadonban.mahdb))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = chitiethoadonban.mahdb }, chitiethoadonban);
        }
      
        // DELETE: api/chitiethoadonbans/5
        [ResponseType(typeof(chitiethoadonban))]
        public IHttpActionResult Deletechitiethoadonban(string id)
        {
            chitiethoadonban chitiethoadonban = db.chitiethoadonbans.Find(id);
            if (chitiethoadonban == null)
            {
                return NotFound();
            }

            db.chitiethoadonbans.Remove(chitiethoadonban);
            db.SaveChanges();

            return Ok(chitiethoadonban);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool chitiethoadonbanExists(string id)
        {
            return db.chitiethoadonbans.Count(e => e.mahdb == id) > 0;
        }
    }
}